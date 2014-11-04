package com.dropbyke

import grails.transaction.Transactional

@Transactional
class RidesService {

	def grailsApplication

	def stopRide(long userId, double lat, double lng, String address, String lockPassword, String message = "") {

		boolean isDebug = grailsApplication.config.com.dropbyke.debug

		if(Math.abs(lat) < 0.00001 || Math.abs(lng) < 0.00001) {
			throw new Exception("Location not set")
		}

		if(!address) {
			throw new Exception("Address not set")
		}

		if(!lockPassword) {
			throw new Exception("Lock password not set")
		}

		User user = User.get(userId)

		if(!user) {
			throw new Exception("User not found")
		}

		def rc = Ride.createCriteria()

		def rides = rc.list {
			eq('user', user)
			eq('stopTime', 0L)
		}

		if(!rides) {
			throw new Exception("No ride found")
		}

		Ride ride = rides.get(0)

		Bike bike = Bike.get(ride.bike.id)

		if(!ride.hasPhoto) {
			if(isDebug) {
				ride.hasPhoto = true
			}
			else {
				throw new Exception("Illegal state: ride have no bike")
			}
		}

		ride.stopTime = System.currentTimeMillis()
		ride.stopLat = lat
		ride.stopLng = lng
		ride.stopAddress = address
		ride.lockPassword = lockPassword
		ride.message = message

		if(ride.save()) {

			bike.lat = lat
			bike.lng = lng
			bike.address = address
			bike.lockPassword = lockPassword
			bike.messageFromLastUser = message
			bike.locked = false

			if(bike.save()) {
				return [
					'bike': bike,
					'ride': ride
				]
			}
			else {
				throw new Exception("Could not save bike")
			}
		}
		else {
			throw new Exception("Could not save ride")
		}
	}

	def getUserCurrentRide(long userId) {

		User user = User.get(userId)

		if(!user) {
			return null
		}

		def rc = Ride.createCriteria()
		def rideAlreadyInProgress = rc.list {
			eq('user', user)
			eq('stopTime', 0L)
		}

		if(!rideAlreadyInProgress) {
			return null
		}

		return rideAlreadyInProgress.get(0)
	}

	def setHasPhoto(long rideId) {
		Ride ride =  Ride.get(rideId)
		ride.hasPhoto = true
		ride.save()
	}
}