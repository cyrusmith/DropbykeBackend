package com.dropbyke

import com.dropbyke.money.Account
import com.odobo.grails.plugin.springsecurity.rest.token.generation.TokenGenerator;
import com.odobo.grails.plugin.springsecurity.rest.token.storage.TokenStorageService;

import grails.transaction.Transactional

@Transactional
class LoginService {

	TokenGenerator tokenGenerator
	TokenStorageService tokenStorageService

	public User registerFacebook(String uid, String name, String email) {

		if(!uid) {
			return null
		}

		User user = User.findByFacebookId(uid)

		if(user == null) {
			user = new User(facebookId:uid, username:uid, password: uid, name:name, email: email, account: new Account(sum: 0L))
			user.save()
			Role role = getRole("ROLE_USER")
			UserRole.create(user, role)
		}

		user
	}

	public User register(String aPhone) {

		def uc = User.createCriteria()
		List users = uc.list {
			eq('phone', aPhone)
			eq('facebookId', "")
		}

		User user = null
		if(users && users.size() > 0) {
			user = users.get(0)
		} else {
			user = new User(username: aPhone, password: aPhone, phone: aPhone, account: new Account(sum: 0L))
			user.save()
			Role role = getRole("ROLE_USER")
			UserRole.create(user, role)
		}

		user
	}

	public String loginFacebook(String uid) {

		String tokenValue = tokenGenerator.generateToken()

		User user = User.findByFacebookId(uid)
		if(!user) {
			throw new IllegalStateException("Could not find user with uid " + uid)
		}

		user.isOnline = true
		user.save()

		def principal = [username: uid]
		tokenStorageService.storeToken(tokenValue, principal)

		return tokenValue
	}

	public String loginPhone(String aPhone) {

		String tokenValue = tokenGenerator.generateToken()

		def uc = User.createCriteria()		
		List users = uc.list {
			eq('phone', aPhone)
			eq('facebookId', "")
		}

		User user = null
		if(users && users.size() > 0) {
			user = users.get(0)
		} 
		
		if(!user) {
			return false
		}

		user.isOnline = true
		user.save()

		def principal = [username: aPhone]
		tokenStorageService.storeToken(tokenValue, principal)

		return tokenValue
	}

	public boolean logout(String aPhone) {

		def tokens = AuthenticationToken.where { username: aPhone }

		User user = User.findByPhone(aPhone)
		if(user) {
			user.isOnline = false
			user.save()
		}

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
		Role role = Role.findByAuthority(aAuthority)

		if(!role) {
			role = new Role(authority: aAuthority)
			role.save();
		}
		role
	}
}