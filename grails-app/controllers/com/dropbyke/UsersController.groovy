package com.dropbyke

import grails.plugin.springsecurity.annotation.Secured
import groovy.util.logging.Log4j;

import org.codehaus.groovy.grails.web.json.JSONObject

import com.odobo.grails.plugin.springsecurity.rest.token.generation.TokenGenerator;
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService;

@Log4j
class UsersController {

	def phoneService
	def loginService

	TokenGenerator tokenGenerator
	TokenStorageService tokenStorageService

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

		String code = data.has("code")?data.getString("code"):null
		String key = data.has("verify_key")?data.getString("verify_key"):null

		if(!code) {
			return render (status: 400, contentType:"application/json") { ["error": "Code not set"] }
		}
		
		if(!key) {
			return render (status: 400, contentType:"application/json") { ["error": "Key not set"] }
		}

		JSONObject resp = phoneService.verifySMSCode(code, key)

		log.debug(resp)
		
		if(!resp) {
			return render (status: 500, contentType:"application/json") { ["error": "Could not verify code. Please try again later."] }
		}
		
		if(!resp.has("verified") || !resp.getBoolean("verified") || !resp.has("tel")) {
			return render (status: 400, contentType:"application/json") { ["error": "Invalid code"] }
		} 

		User user = loginService.register(resp.getString("tel"))
		
		String tokenValue = tokenGenerator.generateToken()				
		
		def principal = [username: 'someuser']
		try {
			tokenStorageService.storeToken(tokenValue, principal)
		}
		catch(Exception err) {
			return	render (status: 500, contentType:"application/json") {
				["error": err.message]
			}
		}

		render (status: 200, contentType:"application/json") { ["access_token": tokenValue] }
	}

	@Secured(['ROLE_USER'])
	def updateProfile() {
		//String name, String email
	}
}