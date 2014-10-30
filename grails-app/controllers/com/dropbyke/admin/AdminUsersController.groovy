package com.dropbyke.admin

import com.dropbyke.User;

import grails.plugin.springsecurity.annotation.Secured;

@Secured(["permitAll"])
class AdminUsersController {

	def index() {		
		[users: User.list(params), usersCount: User.count()]
	}
}
