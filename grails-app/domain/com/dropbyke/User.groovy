package com.dropbyke

class User {

	transient springSecurityService

	String username
	String password
	String email = ""
	String facebookId = ""
	String phone = ""
	String cardNumber = ""
	String cardName = ""
	String cardExpire = ""
	String cardCVS = ""
	boolean phoneVerified = false
	boolean cardVerified = false
	
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static transients = ['springSecurityService']

	static constraints = {
		username blank: false, unique: true
		password blank: false
	}

	static mapping = {
		password column: '`password`'
	}

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
