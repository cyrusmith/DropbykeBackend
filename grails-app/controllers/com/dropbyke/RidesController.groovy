package com.dropbyke

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;
import grails.validation.ValidationException;

class RidesController {

	def springSecurityService
	def ridesService
	def cardService

	@Secured('ROLE_USER')
	def stopRide() {

		def authenticatedUser = springSecurityService.loadCurrentUser()

		JSONObject data = request.JSON

		//double lat, double lng, String address, String lockPassword, String message = ""
		def lat = data.has("lat")?ParseUtils.strToNumber(data.getString("lat"), 0.0):0.0
		def lng = data.has("lng")?ParseUtils.strToNumber(data.getString("lng"), 0.0):0.0
		def address = data.has("address")?data.getString("address"):""
		def lockPassword = data.has("lockPassword")?data.getString("lockPassword"):""
		def message = data.has("message")?data.getString("message"):""

		try {
			def result = ridesService.stopRide(authenticatedUser.id, lat, lng, address, lockPassword, message)
			System.out.println result
			return render (status: 200, contentType:"application/json") { result }
		}
		catch(e) {
			return render (status: 500, contentType:"application/json") {
				[
					"error": e.message
				]
			}
		}
	}

	@Secured('ROLE_USER')
	@Transactional
	def viewRide() {

		def authenticatedUser = springSecurityService.loadCurrentUser()
		long rideId = ParseUtils.strToNumber(params.id)

		def rc = Ride.createCriteria()

		def ride = rc.get {
			eq('id', rideId)
			eq('user', authenticatedUser)
		}

		if(!ride) {
			return render (status: 404, contentType:"application/json")
		}

		Bike bike = Bike.get(ride.bike.id)

		return render (status: 200, contentType:"application/json") {
			[
				'ride': ride,
				'bike': bike
			]
		}
	}

	@Secured('ROLE_USER')
	def uploadPhoto() {

		def authenticatedUser = springSecurityService.loadCurrentUser()
		def photo = request.getFile('photo')

		System.out.println "Ride::uploadPhoto"
		System.out.println photo

		Ride ride = ridesService.getUserCurrentRide(authenticatedUser.id)

		if(!ride) {
			return render (status: 500, contentType:"application/json") {["error": "Could not find ride"]}
		}

		if(photo && photo.bytes) {
			if (ImageUtils.saveRidePhotoFromMultipart(servletContext, photo, ride.id)) {
				ridesService.setHasPhoto(ride.id)
				return render (status: 200, contentType:"application/json") {}
			}
			return render (status: 500, contentType:"application/json") {["error": "Failed to save uploaded file"]}
		}
		return render (status: 400, contentType:"application/json") { ["error": "No file"] }
	}

	@Secured('ROLE_USER')
	def checkout() {

		def authenticatedUser = springSecurityService.loadCurrentUser()

		JSONObject data = request.JSON

		long rideId = data.has("rideId")?data.getLong("rideId"):0L;
		int rating = data.has("rating")?data.getInt("rating"):0;

		if(!rideId) {
			return render (status: 400, contentType:"application/json") { ["error": "Ride id not set"] }
		}

		try {
			ridesService.checkout(rideId, authenticatedUser.id, rating)
			return render (status: 200, contentType:"application/json") { }
		}
		catch(ValidationException e) {
			return render (status: 400, contentType:"application/json") { ["error": e.message] }
		}
		catch(Exception e) {
			return render (status: 500, contentType:"application/json") { ["error": e.message] }
		}

	}


}
