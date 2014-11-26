package com.dropbyke.admin

import java.util.regex.Pattern;

import com.dropbyke.LoginService;
import com.dropbyke.Role;
import com.dropbyke.User;
import com.dropbyke.UserRole;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

@Secured(["ROLE_ADMIN"])
class AdminUsersController {

	def loginService

	def index() {
		List users = User.list(params)
		[users: users, usersCount: User.count()]
	}

	@Transactional
	def edit() {

		if(request.get) {
			if(params.containsKey("id")) {
				User user = User.get(params.getLong("id"))
				if(!user) {
					return sendError(code:400)
				}
				return render(view: 'edit', model: [
					id: params.id,
					phone: user.phone,
					name: user.name,
					email: user.email
				])
			}
			else {
				return render(view: 'edit')
			}
		}

		if(!params.phone) {
			flash.error = "Phone not set"
			return render(view: 'edit', model: [
				id: params.id,
				name: params.name,
				email: params.email
			])
		}

		String phone = params.phone.replaceAll(Pattern.compile("[^0-9]"), "")

		if(phone.size()!=11) {
			flash.error = "Illegal phone format. Expected 11-digit input"
			return render(view: 'edit', model: [
				id: params.id,
				phone: params.phone,
				name: params.name,
				email: params.email
			])
		}

		User user

		if(params.id) {
			user = User.get(params.id)
			if(!user) {
				return sendError(code:404)
			}

			if(user.phone!=phone) {
				def existingUser = User.where { phone == phone }
				if(existingUser) {
					flash.error = "User with phone " + phone + " already exists"
					return render(view: 'edit', model: [
						id: params.id,
						phone: params.phone,
						name: params.name,
						email: params.email
					])
				}
			}
		}
		else {
			user = loginService.register(phone)
		}

		user.phone = phone
		user.name = params.name
		user.email = params.email

		if(user.save()) {
			flash.message = "User created successfully"
			redirect(action: 'index')
		}
		else {
			flash.error = "Failed to create user"
			return render(view: 'edit', model: [
				id: params.id,
				phone: params.phone,
				name: params.name,
				email: params.email
			])
		}
	}

	@Transactional
	def add() {
		return this.edit()
	}

	@Transactional
	def delete() {
/*
		if(!params.id) return sendError(code: 400)
		
		Role role = Role.findByAuthority('ROLE_USER')
		
		User user = User.get(params.id)
		
		UserRole.remove(user, role)
		
		loginService.logout(user.phone)
		if(user.delete()) {
			flash.error = "Failed to delete user"
		}
		else {
			flash.message = "User deleted"
		}
		redirect(action:"index")
	*/	
	}
}
