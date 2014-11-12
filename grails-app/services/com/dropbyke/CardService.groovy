package com.dropbyke

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import grails.transaction.Transactional
import groovy.util.logging.Log4j;

@Log4j
class CardService {

	def grailsApplication

	def createCustomer(String cardNumber, String name, String expireMonth, String expireYear, String cvc) {

		log.debug("Create customer with: {" + cardNumber + ", " + name + ", " + cvc + "}")

		Stripe.apiKey = grailsApplication.config.com.dropbyke.stripeApiKey;

		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("description", "Customer for " + name);

		Map<String, Object> cardParams = new HashMap<String, Object>();
		cardParams.put("number", cardNumber);
		cardParams.put("name", name);
		cardParams.put("exp_month", expireMonth);
		cardParams.put("exp_year", expireYear);
		cardParams.put("cvc", cvc);

		customerParams.put("card", cardParams);

		try {
			return Customer.create(customerParams)
		}
		catch(CardException e) {
			log.error(e.message)
			return e.message
		}
		catch(StripeException e) {
			log.error(e)
			return null
		}
	}

	@Transactional
	def checkout(long userId, long rideId, long amount) {

		Stripe.apiKey = grailsApplication.config.com.dropbyke.stripeApiKey;

		if(amount < 50) {
			throw new IllegalArgumentException("Amount is too small");
		}

		User user = User.get(userId)
		Ride ride = Ride.get(rideId)

		if(!user) {
			throw new IllegalArgumentException("Could not find user");
		}

		if(!ride) {
			throw new IllegalArgumentException("Could not find ride");
		}

		if(!user.stripeCustomerId) {
			throw new IllegalStateException("Stripe customer id is not set");
		}

		Map<String, Object> chargeParams = new HashMap<String, Object>();

		chargeParams.put("amount", amount);
		chargeParams.put("currency", "usd");
		chargeParams.put("customer", user.stripeCustomerId);
		chargeParams.put("description", "Charge for test@example.com");

		Map<String, Object> initialMetadata = new HashMap<String, Object>();
		initialMetadata.put("phone", user.phone)

		if(user.email && !"".equals(user.email)) {
			initialMetadata.put("email", user.email)
		}
		
		if(user.name && !"".equals(user.name)) {
			initialMetadata.put("name", user.name)
		}
		else {
			initialMetadata.put("name", user.username)
		}				

		chargeParams.put("metadata", initialMetadata)

		Charge charge = Charge.create(chargeParams)
		
		System.out.println charge

		com.dropbyke.Charge dbCharge = new com.dropbyke.Charge(
				amount: amount,
				user: user,
				ride: ride,
				cardNumber: user.cardNumber,
				stripeChargeId: charge.getId(),
				timestamp: System.currentTimeMillis()
				)
		dbCharge.save()

		if(!charge) {
			throw new Exception("Could not create charge")
		}

		ride.charged = true
		ride.save()
		
		return charge
	}

}