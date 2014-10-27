package com.dropbyke

import grails.plugin.springsecurity.annotation.Secured;
import groovy.util.logging.Log4j;

@Log4j
class CardController {

	@Secured(['ROLE_USER'])
	def addCard() {
		return render(status: 200, contentType: "application/json") {
			[data: 'OKOKO']
		}
	}
	
}