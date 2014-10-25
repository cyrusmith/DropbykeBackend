package com.dropbyke

import grails.transaction.Transactional

@Transactional
class LoginService {

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