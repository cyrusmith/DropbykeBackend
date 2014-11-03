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
		
		Ride ride = Ride.findByBike(bike, [max: 1, sort: "stopTime", order: "desc"])
		
		
		
	}
}
