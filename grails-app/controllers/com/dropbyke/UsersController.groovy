package com.dropbyke

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional;
import groovy.util.logging.Log4j;

import org.codehaus.groovy.grails.web.json.JSONObject

import com.odobo.grails.plugin.springsecurity.rest.token.generation.TokenGenerator;
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService;

@Log4j
class UsersController {

	def phoneService
	def loginService
	def springSecurityService
	def userService

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

		String tokenValue = loginService.login(user.phone);

		def userInfo = userService.getUserInfo(user.id)

		render (status: 200, contentType:"application/json") {
			[
				"user_info": userInfo,
				"access_token": tokenValue
			]
		}
	}

	@Secured(['ROLE_USER'])
	def viewProfile() {

		def authenticatedUser = springSecurityService.loadCurrentUser()
		def info = userService.getUserInfo(authenticatedUser.id)
		info["timestamp"] = System.currentTimeMillis()
		render (status: 200, contentType:"application/json") { info }
	}

	@Secured(['ROLE_USER'])
	@Transactional
	def updateProfile() {
		JSONObject data = request.JSON

		def authenticatedUser = springSecurityService.loadCurrentUser()
		def name = data.has("name")?data.getString("name"):""
		def email = data.has("email")?data.getString("email"):""

		User user = User.get(authenticatedUser.id)

		if(user && name && email) {

			user.name = name
			user.email = email
			if(user.save()) {
				return	render (status: 200, contentType:"application/json") { ["profile": user] }
			}
			else {
				return	render (status: 500, contentType:"application/json") { ["error": "Could not save user"] }
			}
		}

		return render (status: 400, contentType:"application/json") { ["error": "Empty parameters"] }
	}

	@Secured(['ROLE_USER'])
	def uploadPhoto() {
		def authenticatedUser = springSecurityService.loadCurrentUser()
		def photo = request.getFile('photo')
		System.out.println "uploadPhoto"
		System.out.println photo
		if(photo && photo.bytes) {
			if (ImageUtils.saveUserPhotoFromMultipart(servletContext, photo, authenticatedUser.id)) {
				return render (status: 200, contentType:"application/json") {}
			}
			return render (status: 500, contentType:"application/json") {["error": "Failed to save uploaded file"]}
		}
		return render (status: 400, contentType:"application/json") { ["error": "No file"] }
	}
}