package com.dropbyke

import java.text.NumberFormat;

import javax.transaction.Transaction;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.hibernate.criterion.CriteriaSpecification;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

class BikesController {

	def dataSource
	def bikesService
	def springSecurityService

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
			return render(status: 200, contentType:"application/json") { ["ride": [
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
				]] }
		}
		catch(e) {
			e.printStackTrace()
			return render(status: 500, contentType:"application/json") { ["error": e.message] }
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