package com.dropbyke.admin

import com.dropbyke.Bike;

import grails.plugin.springsecurity.annotation.Secured;

@Secured(["permitAll"])
class AdminBikesController {

	private static final okcontents = [
		'image/png',
		'image/jpeg',
		'image/gif'
	]

	def index() {
		[bikes: Bike.list(params), bikesCount: Bike.count()]
	}

	def add() {

		if(request.get) {
			return
		}

		def photo = request.getFile('photo')

		Bike bike = new Bike(title: params.title)

		if (!okcontents.contains(photo.getContentType())) {
			flash.error = "Photo must be one of: ${okcontents}"
			render(view:'add', model: [
				title:bike.title
			])
			return
		}
	}
}
