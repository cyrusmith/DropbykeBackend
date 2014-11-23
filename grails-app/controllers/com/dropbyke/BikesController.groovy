package com.dropbyke

import java.text.NumberFormat;

import javax.transaction.Transaction;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.web.multipart.MultipartFile;

import com.dropbyke.FileUploadService.Folder;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

class BikesController {

	def dataSource
	def bikesService
	def springSecurityService
	def bikeShareService
	def fileUploadService

	@Secured(['permitAll'])
	def bikesInArea() {

		double lat1 = 0.0
		double lat2 = 0.0
		double lng1 = 0.0
		double lng2 = 0.0

		lat1 = ParseUtils.strToNumber(params["lat1"], 0.0)
		lat2 = ParseUtils.strToNumber(params["lat2"], 0.0)
		lng1 = ParseUtils.strToNumber(params["lng1"], 0.0)
		lng2 = ParseUtils.strToNumber(params["lng2"], 0.0)

		System.out.println "bikesInArea" + [lat1, lat2, lng1, lng2]

		def bikes
		def bc = Bike.createCriteria()

		if((Math.abs(lng1 - lng2) > 0.0000001) && Math.abs(lat1 - lat2) > 0.0000001) {

			bikes = bc.list {
				maxResults(20)
				gt('lat', lat1)
				lt('lat', lat2)
				gt('lng', lng1)
				lt('lng', lng2)
				eq('locked', false)
			}
		}
		else {

			bikes = bc.list {
				maxResults(20)
				eq('locked', false)
			}
		}

		render(status: 200, contentType: "application/json") { ["bikes": bikes] }
	}

	@Secured(['ROLE_USER'])
	@Transactional
	def view() {

		if(!params.id) {
			return render(status: 404, contentType:"application/json") { ["error": "ID not set"] }
		}

		def id = params.id.toLong()

		Bike bike = Bike.get(id)

		if(!bike) {
			return render(status: 404, contentType:"application/json") { ["error": "Not found"] }
		}

		if(bike.locked) {
			return render(status: 400, contentType:"application/json") { ["error": "Bike is locked"] }
		}

		def rc = Ride.createCriteria()
		def rides = rc.list {
			maxResults(1)
			gt('stopTime', 0L)
			order("stopTime", "desc")
		}

		def ride = null;
		if(rides && rides.size()) {
			ride = rides.get(0)
		}
		System.out.println "ride="+ride + ", bike=" + bike
		return render(status: 200, contentType:"application/json") {
			["bike": bike, "ride": ride]
		}
	}

	@Secured(['ROLE_USER'])
	@Transactional
	def startUsage() {
		def authenticatedUser = springSecurityService.loadCurrentUser()

		JSONObject data = request.JSON

		def bikeId = data.has("bikeId")?data.getLong("bikeId"):0
		if(!bikeId) {
			return render(status: 404, contentType:"application/json") { ["error": "Id not set"] }
		}

		Bike bike = Bike.get(bikeId)

		if(!bike) {
			return render(status: 404, contentType:"application/json") { ["error": "Bike not found"] }
		}

		try {
			Ride ride = bikesService.startUsage(authenticatedUser.id, bikeId)
			bike.locked = true
			bike.save()
			return render(status: 200, contentType:"application/json") {
				["ride": [
						'id': ride.id,
						'title': bike.title,
						'rating': bike.rating,
						'startTime': ride.startTime,
						'startLat': bike.lat,
						'startLng': bike.lng,
						'price': bike.priceRate,
						'lockPassword': bike.lockPassword,
						'message': bike.messageFromLastUser,
						'lastRideId': bike.lastRideId,
						'hasPhoto': false
					]]
			}
		}
		catch(e) {
			return render(status: 500, contentType:"application/json") { ["error": e.message] }
		}
	}

	@Secured(['ROLE_USER'])
	def bikesList() {
	}

	@Secured(['ROLE_USER'])
	def bikeInfo() {
		Bike bike = Bike.get(params.id)
		if(!bike) {
			return render(status: 404, contentType:"application/json") { ["error": "Bike not found"] }
		}
		return render(status: 200, contentType:"application/json") { ["bike": bike] }
	}

	@Secured(['ROLE_USER'])
	def saveBike() {

		def authUser = springSecurityService.loadCurrentUser()

		JSONObject json = request.JSON

		println "saveBike json " + json
		println "saveBike params " + params

		boolean active
		String name
		String sku
		int price
		String lockPassword
		String address
		double lat
		double lng
		String message

		if(!json.isEmpty()) {
			if(!(json.has("active") &&
			json.has("title") &&
			json.has("sku") &&
			json.has("priceRate") &&
			json.has("lockPassword") &&
			json.has("address") &&
			json.has("lat") &&
			json.has("lng") &&
			json.has("messageFromLastUser"))) {
				return render(status: 400, contentType:"application/json") { ["error": "Some fields are not set"] }
			}

			active = json.getBoolean("active")
			name = json.getString("title")
			sku = json.getString("sku")
			price = json.getInt("priceRate")
			lockPassword = json.getLong("lockPassword")
			address = json.getString("address")
			lat = json.getLong("lat")
			lng = json.getLong("lng")
			message = json.getString("messageFromLastUser")
		}
		else {
			if(!(params.containsKey("active") &&
			params.containsKey("title") &&
			params.containsKey("sku") &&
			params.containsKey("priceRate") &&
			params.containsKey("lockPassword") &&
			params.containsKey("address") &&
			params.containsKey("lat") &&
			params.containsKey("lng") &&
			params.containsKey("messageFromLastUser"))) {
				return render(status: 400, contentType:"application/json") { ["error": "Some fields are not set"] }
			}

			active = params["active"] && true
			name = params["title"]
			sku = params["sku"]
			price = ParseUtils.strToInt(params["priceRate"])
			lockPassword = params["lockPassword"]
			address = params["address"]
			lat = ParseUtils.strToLong(params["lat"])
			lng = ParseUtils.strToLong(params["lng"])
			message = params["messageFromLastUser"]
		}

		MultipartFile photo = request.getFile("photo")

		if(!photo || photo.isEmpty()) {
			return render(status: 400, contentType:"application/json") { ["error": "Photo not set"] }
		}

		try {
			Bike bike = bikeShareService.addBike(sku, name, price, lat, lng, address, lockPassword, message)
			if(fileUploadService.savePhoto(photo, Folder.BIKES, bike.id)) {
				if(bikeShareService.setUserBikeActive(bike.id, authUser.id, active)) {
					return render(status: 500, contentType:"application/json") { ["error": "Bike saved but failed to set active status"] }
				}
				return render(status: 200, contentType:"application/json") { ["bike": bike] }
			}
			else {
				return render(status: 500, contentType:"application/json") { ["error": "Failed to save photo"] }
			}
		}
		catch(IllegalArgumentException e) {
			return render(status: 400, contentType:"application/json") { ["error": e.message] }
		}
		catch(e) {
			return render(status: 500, contentType:"application/json") { ["error": e.message] }
		}
	}

	@Secured(['ROLE_USER'])
	def addUserPhoto() {

		//TODO

		def authenticatedUser = springSecurityService.loadCurrentUser()

		def photo = request.getFile("photo")

		if(!photo || photo.isEmpty()) {
			return render(status: 400, contentType:"application/json") { ["error": "Photo not set"] }
		}

		if(params.id) {

			Bike bike = Bike.get(params.id)
			if(!bike) {
				return render(status: 404, contentType:"application/json") { ["error": "Bike not found"] }
			}

			if(bike.user.id != authenticatedUser.id) {
				return render(status: 404, contentType:"application/json") { ["error": "Bike not found for current user"] }
			}

			try {
				if(fileUploadService.savePhoto(photo, Folder.BIKES, params.id)) {
					return render(status: 200, contentType:"application/json") { }
				}
				else {
					return render(status: 500, contentType:"application/json") { ["error": "Failed to save photo"] }
				}
			}
			catch(e) {
				return render(status: 500, contentType:"application/json") { ["error": e.message] }
			}

		}
		else { //tmp for new
		}
	}

	/**
	 * Using haversine formula - http://en.wikipedia.org/wiki/Haversine_formula
	 * @return
	 */
	private Map calcGeoBoundsFromSphereDistance(double lat, double lon, double dist) {

		def R = 6371000.0 //Earth radius
		def D = 2.0*R //Earth diam

		lat = lat*Math.PI/180
		lon = lon*Math.PI/180

		def deltaLng = 2.0*asin(sin(dist/D)/cos(lat))
		def deltaLat = dist/R

	}
}