package com.dropbyke

import grails.plugin.springsecurity.annotation.Secured;


class BikesController {
	
	@Secured(["permitAll"])
	def avatarImage() {
		def bike = Bike.get(params.id)
		if (!bike || !bike.photo) {
			response.sendError(404)
			return
		}
		response.contentType = bike.photoType
		response.contentLength = bike.photo.size()
		OutputStream out = response.outputStream
		out.write(bike.photo)
		out.close()
	}
}