package com.dropbyke.admin

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.dropbyke.Bike;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

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

	@Transactional
	def edit() {

		if(request.get) {
			Bike bike = Bike.get(params.id)
			if(!bike) {
				response.sendError(404)
				return
			}
			render(view:'add', model: [
				id:bike.id,
				hasPhoto:bike.photo,
				title:bike.title,
				lat:bike.lat,
				lon:bike.lon
			])
			return
		}

		Bike bike = Bike.get(params.id)

		def photo = request.getFile('photo')

		if(photo.bytes) {
			if (!okcontents.contains(photo.getContentType())) {
				flash.error = "Photo must be one of: ${okcontents}"
				render(view:'add', model: [
					id: bike.id,
					hasPhoto:bike.photo,
					title:bike.title,
					lat:bike.lat,
					lon:bike.lon
				])
				return
			}
			bike.photo = photo.bytes
			bike.photoType = photo.contentType
		}


		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US)
		formatter.setParseIntegerOnly false

		if(params.lat && params.lon) {
			params.lat = formatter.parse(params.lat);
			params.lon = formatter.parse(params.lon);
		}
		else {
			params.lat = 0.0
			params.lon = 0.0;
		}

		bike.lat = params.lat
		bike.lon = params.lon

		bike.save()

		flash.message = "Bike " + params.title + " successfully saved"
		redirect(action: "index")
	}

	@Transactional
	def add() {

		if(request.get) {
			return
		}

		def photo = request.getFile('photo')

		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US)
		formatter.setParseIntegerOnly false

		if(params.lat && params.lon) {
			params.lat = formatter.parse(params.lat);
			params.lon = formatter.parse(params.lon);
		}
		else {
			params.lat = 0.0
			params.lon = 0.0;
		}

		Bike bike = new Bike(title: params.title)
		bike.lat = params.lat
		bike.lon = params.lon

		if(photo.bytes) {
			if (!okcontents.contains(photo.getContentType())) {
				flash.error = "Photo must be one of: ${okcontents}"
				render(view:'add', model: [
					title:bike.title,
					lat:bike.lat,
					lon:bike.lon
				])
				return
			}

			bike.photo = photo.bytes
			bike.photoType = photo.contentType
		}

		bike.save()

		flash.message = "Bike " + params.title + " successfully added"
		redirect(action: "index")
	}
}
