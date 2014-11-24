package com.dropbyke

import grails.transaction.Transactional

@Transactional
class BikeShareService {

	def addBike(long userId, String sku, String name, int price, double lat, double lng, String address, String lockPassword, String message, boolean active) throws Exception {

		User user = User.get(userId)

		if(!user) {
			throw new IllegalArgumentException("User not exists")
		}

		if(!(name && sku && price && lockPassword && address && message)) {
			throw new IllegalArgumentException("Some fields are not set")
		}

		Bike bike = new Bike(
				user: user,
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

		return bike
	}

	def userBikesInArea(long userId, double lat1, double lng1, double lat2, double lng2) throws Exception {

		System.out.println "userBikes" + [lat1, lat2, lng1, lng2]

		User user = User.get(userId)

		if(!user) {
			throw new IllegalArgumentException("User not found")
		}
		
		def bc = Bike.createCriteria()

		def bikes = bc.list {
			maxResults(20)
			eq('active', true)
			eq('user', user)
			gt('lat', lat1)
			lt('lat', lat2)
			gt('lng', lng1)
			lt('lng', lng2)
		}
		
		println bikes
		
		return bikes
				
	}
}
