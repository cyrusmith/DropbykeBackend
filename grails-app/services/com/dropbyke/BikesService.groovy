package com.dropbyke

import grails.transaction.Transactional

@Transactional
class BikesService {

	def startUsage(long userId, long bikeId) {

		Bike bike = Bike.get(bikeId)
		User user = User.get(userId)

		if(!bike) {
			throw new Exception("Bike not found")
		}

		if(!user) {
			throw new Exception("User not found")
		}

		if(bike.locked) {
			throw new Exception("Bike already in use")
		}

		def rc = Ride.createCriteria()

		def rideAlreadyInProgress = rc.list {
			eq('bike', bike)
			eq('stopTime', 0L)
		}

		if(rideAlreadyInProgress || rideAlreadyInProgress.size()) {
			throw new Exception("Bike already has ride in progres")
		}
		
		Ride ride = new Ride(user: user, bike: bike, startLat: bike.lat, startLng: bike.lng, startAddress: bike.address)

		if(!ride.save()) {
			throw new Exception("Failed to create ride")
		}
		
		return ride
	}
}
