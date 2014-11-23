package com.dropbyke

import grails.transaction.Transactional

@Transactional
class BikeShareService {

	def addBike(String sku, String name, int price, double lat, double lng, String address, String lockPassword, String message) throws Exception {

		if(!(name && sku && price && lockPassword && address && message)) {
			throw new IllegalArgumentException()
		}

		Bike bike = new Bike(
				sku: sku,
				title:name,
				priceRate:price,
				lat:lat,
				lng:lng,
				address: address,
				lockPassword: lockPassword,
				messageFromLastUser:message)

		if(bike.save() && UserBike.create(authenticatedUser, bike)) {
			return bike
		}

		throw new Exception("Failed to save bike")
	}

	def setUserBikeActive(long bikeId, long userId, boolean isActive) {
		Bike bike = Bike.load(bikeId)
		User user = User.load(userId)
		UserBike userBike = UserBike.getByBikeAndUser(bike, user)
		if(!userBike) {
			return false
		}
		userBike.active = isActive
		return userBike.save()
	}
}
