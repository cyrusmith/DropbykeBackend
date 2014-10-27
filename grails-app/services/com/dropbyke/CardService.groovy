package com.dropbyke

import com.stripe.Stripe;
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;

import grails.transaction.Transactional
import groovy.util.logging.Log4j;

@Log4j
class CardService {

	def createCustomer(String cardNumber, String name, String expireMonth, String expireYear, String cvc) {
		
		log.debug("Create customer with: {" + cardNumber + ", " + name + ", " + cvc + "}")
		
		Stripe.apiKey = "sk_test_BQokikJOvBiI2HlWgH4olfQ2";

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
	
	
		
}