package com.dropbyke.admin

import grails.plugin.springsecurity.annotation.Secured;

import com.dropbyke.Charge;

@Secured(["permitAll"])
class AdminChargesController {

    def index() {		
		[charges: Charge.list(params), chargesCount: Charge.count()]
	}
	
}
