package com.dropbyke

import com.odobo.grails.plugin.springsecurity.rest.token.generation.TokenGenerator;
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService;

import grails.transaction.Transactional

@Transactional
class LoginService {

	TokenGenerator tokenGenerator
	TokenStorageService tokenStorageService

	public User register(String aPhone) {

		User user = this.getExistingUser(aPhone)

		if(user == null) {
			user = new User(username: aPhone, password: aPhone, phone: aPhone)
			user.save()
			Role role = getRole("ROLE_USER")
			UserRole.create(user, role)
		}

		user
	}

	public String login(String aPhone) {

		String tokenValue = tokenGenerator.generateToken()

		def principal = [username: aPhone]
		try {
			tokenStorageService.storeToken(tokenValue, principal)
		}
		catch(Exception err) {
			return false
		}

		return tokenValue
	}

	public boolean logout(String aPhone) {

		def tokens = AuthenticationToken.where { username : aPhone }

		if(!tokens) return false

		try {
			for(def token in tokens) {
				token.delete()
			}
			return true
		}
		catch(e) {
			return false
		}
	}

	public User getExistingUser(String aPhone) {
		List<User> users = User.where { username == aPhone }.findAll()
		if(users!=null && !users.isEmpty()) {
			return users[0]
		}
		return null
	}

	private Role getRole(String aAuthority) {
		Role role = Role.where { authority:aAuthority }.get()

		if(!role) {
			role = new Role(authority: aAuthority)
			role.save();
		}
		role
	}
}