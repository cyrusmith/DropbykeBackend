package com.dropbyke

import org.codehaus.groovy.grails.web.json.JSONObject;

import com.stripe.model.Customer;

import grails.plugin.springsecurity.annotation.Secured;
import groovy.util.logging.Log4j;

@Log4j
class CardController {

	def cardService
	def userService
	def springSecurityService

	@Secured(['ROLE_USER'])
	def addCard() {

		JSONObject data = request.JSON

		String number = data.has("number")?data.getString("number"): null;
		String name = data.has("name")?data.getString("name"): null;
		String expire = data.has("expire")?data.getString("expire"): null;
		String cvc = data.has("cvc")?data.getString("cvc"): null;

		if(!number) {
			return render(status: 400, contentType: "application/json") { [error: "Number not set"] }
		}

		if(!name) {
			return render(status: 400, contentType: "application/json") { [error: "Name not set"] }
		}

		if(!expire) {
			return render(status: 400, contentType: "application/json") { [error: "Expire date not set"] }
		}

		def (month, year) = expire.tokenize( '/' )

		if(!month || !year) {
			return render(status: 400, contentType: "application/json") { [error: "Expire date not in valid format"] }
		}

		if(!cvc) {
			return render(status: 400, contentType: "application/json") { [error: "CVC not set"] }
		}

		try {
			def customer = cardService.createCustomer(number, name, month, year, cvc);

			if(customer instanceof String) {
				return render(status: 400, contentType: "application/json") { [error: customer] }
			}
			else if(!(customer instanceof Customer)) {
				return render(status: 500, contentType: "application/json") { [error: "Could not save card info remotely. Please try again later."] }
			}

			Customer cust = (Customer)customer;

			def authenticatedUser = springSecurityService.loadCurrentUser()

			if(userService.setCardInfo(authenticatedUser.id, number, name, expire, cvc, cust.getId())) {
				return render(status: 200, contentType: "application/json") { customer }
			}
			else {
				return render(status: 500, contentType: "application/json") { [error: "Could not save card info. Please try again later."] }
			}
			
		}
		catch(Exception e) {
			return render(status: 500, contentType: "application/json") { [error: e.message] }
		}
	}
}