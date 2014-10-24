package com.dropbyke

import grails.plugin.springsecurity.annotation.Secured

import org.codehaus.groovy.grails.web.json.JSONObject

class UsersController {

	def phoneService

	static allowedMethods = [registerPhone:'POST']

	@Secured(['permitAll'])
	def registerPhone() {

		JSONObject data = request.JSON

		String phone = data.has("phone")?data.getString("phone"):null

		if(!phone) {

			return render (status: 400, contentType:"application/json") { ["error": "Phone not set"] }
		}
		JSONObject resp = phoneService.sendSMS(phone)

		if(!resp || !resp.has("id")) {
			return render (status: 500, contentType:"application/json") { ["error": "Could not submit SMS. Please try again later."] }
		}
		
		render (status: 200, contentType:"application/json") { ["request_key": resp.getString("id")] }
	}

	@Secured(['permitAll'])
	def verifyCode() {

		JSONObject data = request.JSON

		String code = data.has("phone")?data.getString("code"):null

		if(!code) {
			return render (status: 400, contentType:"application/json") { ["error": "Code not set"] }
		}

		JSONObject resp = phoneService.verifySMSCode(code)

		if(!resp) {
			return render (status: 500, contentType:"application/json") { ["error": "Could not verify code. Please try again later."] }
		}

		//TODO generate token
		
		render (status: 200, contentType:"application/json") { ["data": []] }
		
	}

	@Secured(['ROLE_USER'])
	def updateProfile() {
		//String name, String email
	}
}