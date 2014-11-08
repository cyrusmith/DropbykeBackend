package com.dropbyke.admin

import grails.plugin.springsecurity.annotation.Secured;

import com.dropbyke.Charge;

@Secured(["ROLE_ADMIN"])
class AdminChargesController {

    def index() {		
		[charges: Charge.list(params), chargesCount: Charge.count()]
	}
	
}
