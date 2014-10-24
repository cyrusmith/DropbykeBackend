package com.dropbyke

import grails.transaction.Transactional

@Transactional
class LoginService {

	public User register(String email, String username, String password) {

		if(this.isUserExists(email, username)) {
			return null
		}

		User user = new User(username : username, email: email, password: password);
		user.save();
		Role role = getRole("ROLE_USER")
		UserRole.create(user, role)
		user
	}

	public boolean isUserExists(String aEmail, String aUsername) {
		List<User> users = User.where {
			username == aUsername ||
					email == aEmail
		}.findAll()
		if(users!=null && !users.isEmpty()) {
			return true
		}
		return false
	}

	private Role getRole(String authority) {
		Role role = Role.where { authority:authority }.get()

		if(!role) {
			role = new Role(authority: authority)
			role.save();
		}
		role
	}
}