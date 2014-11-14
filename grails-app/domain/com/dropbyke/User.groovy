package com.dropbyke

class User {

	transient springSecurityService

	static hasMany = [rides: Ride]
	
	String username	
	String password
	String name = ""
	String email = ""
	String facebookId = ""
	String phone = ""
	String cardNumber = ""
	String cardName = ""
	String cardExpire = ""
	String cardCVC = ""
	String stripeCustomerId = ""
	
	boolean editedOnce = false

	boolean isOnline = false
	boolean cardVerified = false
	
	boolean shareFacebook = true

	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = { password column: '`password`' }

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role }
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}
}
