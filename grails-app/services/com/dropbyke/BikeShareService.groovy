package com.dropbyke

import grails.transaction.Transactional

@Transactional
class BikeShareService {

	def addBike(long userId, String sku, String name, int price, double lat, double lng, String address, String lockPassword, String message, boolean active) throws Exception {

		System.out.println "" + [
			userId,
			sku,
			name,
			price,
			lat,
			lng,
			address,
			lockPassword,
			message
		]

		User user = User.load(userId)

		if(!(name && sku && price && lockPassword && address && message)) {
			throw new IllegalArgumentException("Some fields are not set")
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

		if(!bike.save()) {
			def errors = bike.errors.allErrors
			if(errors && errors.size() > 0) {
				String fld = errors.get(0).field
				if("sku".equals(fld)) {
					throw new Exception("Serial is not unique")
				}
				else {
					throw new Exception(fld + " is invalid")
				}
			}

			throw new Exception("Failed to save bike")
		}

		if(UserBike.create(user, bike, active)) {
			return bike
		}
		else {
			throw new Exception("Failed to save new bike")
		}
	}

	def setUserBikeActive(long bikeId, long userId, boolean isActive) {
		Bike bike = Bike.load(bikeId)
		User user = User.load(userId)
		UserBike userBike = UserBike.findByBikeAndUser(bike, user)
		if(!userBike) {
			return false
		}
		userBike.active = isActive
		if(!userBike.save()) {
			userBike.errors.each { println it }
			return false
		}
		return true
	}
}
