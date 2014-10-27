package com.dropbyke

import grails.transaction.Transactional

@Transactional
class UserService {

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

		user.save()
		return true
	}
}