package com.dropbyke

import grails.transaction.Transactional

@Transactional
class UserService {

	def fileUploadService

	def setCardInfo(long userId, String cardNumber, String cardName, String cardExpire, String cardCVC, String stripeCustomerId) {

		User user = User.get(userId)

		if(!user) {
			log.error("User with id  " + userId + " not found")
			return false
		}

		user.cardNumber = cardNumber
		user.cardName = cardName
		user.cardExpire = cardExpire
		user.cardCVC = cardCVC
		user.stripeCustomerId = stripeCustomerId
		user.cardVerified = true

		user.save()
		return true
	}

	def getUserInfo(long userId) {

		User user = User.get(userId)

		def rc = Ride.createCriteria()

		def rides = rc.list {
			eq('user', user)
			or {
				eq('stopTime', 0L)
				eq('complete', false)
			}
		}

		def ride = null
		def bike = null
		def rideImageExists = false

		if(rides) {
			ride = rides.get(0)
			bike = Bike.get(ride.bike.id)
		}

		return [
			"user": user,
			"ride": ride,
			"bike": bike
		]
	}

	def setPhone(long userId, String phone) {
		
		User user = User.get(userId)
		
		if(!user || user.phone) {
			return false
		}

		user.phone = phone

		user.save()
	}
}