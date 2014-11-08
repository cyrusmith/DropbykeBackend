package com.dropbyke

import grails.transaction.Transactional
import grails.validation.ValidationException;

@Transactional
class RidesService {

	def grailsApplication
	def cardService

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
			bike.messageFromLastUser = message ? message  : ""
			bike.locked = false
			bike.lastRideId = ride.id
			bike.lastUserPhone = user.phone
			
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

	def checkout(long rideId, long userId, int rating) {

		Ride ride = Ride.get(rideId);
		User user = User.get(userId);

		if(!ride) {
			throw new ValidationException("Ride not found")
		}
		
		if(ride.charged) {
			throw new IllegalStateException("Ride already charged")
		}
		
		if(!ride.stopTime) {
			throw new IllegalStateException("Ride not finished")
		}
		
		if(ride.stopTime - ride.startTime  < 0) {
			throw new IllegalStateException("Invalid time period")
		}

		Bike bike = Bike.get(ride.bike.id)

		if(!user) {
			throw new ValidationException("User not found")
		}

		if(rating < 0 || rating > 5) {
			throw new ValidationException("Rating has invalid value")
		}

		BikeRating existingBikeRating = BikeRating.findByUserAndRide(user, ride)

		if(existingBikeRating) {
			throw new ValidationException("Rating already complete")
		}

		BikeRating rideRating = new BikeRating(user:user, ride:ride, bike:bike, rating:rating)
		if(!rideRating.save()) {
			throw new Exception("Could not save rating")
		}

		def rrc = BikeRating.createCriteria()
		def bikeRatingsSum = rrc.list {
			eq('bike', bike)
			projections { sum "rating" }
		}

		def bikeRatingsCount = BikeRating.countByBike(bike)
		bikeRatingsSum = bikeRatingsSum.get(0)

		double sum = rating
		double count = 1

		sum = sum + bikeRatingsSum
		count = count + bikeRatingsCount

		bike.rating = sum / count
		bike.save()				
		
		long amount = Math.ceil((ride.stopTime - ride.startTime)*bike.priceRate/36000.0)
		
		if(amount < 50) {
			return true
		}
		
		cardService.checkout(userId, rideId, amount)		
		
	}
}