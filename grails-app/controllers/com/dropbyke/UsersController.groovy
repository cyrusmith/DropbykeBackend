package com.dropbyke

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

class UsersController {

	@Secured(['permitAll'])
	def register() {
		
		JSON data = request.JSON
		
		int userId = data.getInt("user_id")
		String token = data.getString("facebook_token")
		
		//TODO make fb api request
		
		render (status: 200, contentType:"application/json") {
			["status": "ok", "req": request.JSON]
		}
		
	}
}
