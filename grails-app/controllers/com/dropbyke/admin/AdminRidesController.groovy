package com.dropbyke.admin

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dropbyke.Bike;
import com.dropbyke.Ride;
import com.dropbyke.User;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

@Secured(["ROLE_ADMIN"])
class AdminRidesController {

	def index() {
		[rides: Ride.list(params), ridesCount: Ride.count()]
	}

	
	@Transactional
	def edit() {

		if(request.get) {
			if(params.id) {
				Ride ride = Ride.get(params.id)
				if(!ride) {
					return response.sendError(404)
				}
				return render(view:'edit', model: ["ride": ride])
			}

			return render(view:'edit', model: ["ride": params])
		}
/*
		Ride ride
		if(params.id) {
			ride = Ride.get(params.id)
			if(!ride) {
				response.sendError(404)
				return
			}
		}
		else {
			ride = new Ride()
		}

		ride.startTime = strToInt(params.startTime);
		ride.stopTime = strToInt(params.stopTime);

		ride.startLat = strToNumber(params.startLat);
		ride.startLng = strToNumber(params.startLng);

		ride.stopLat = strToNumber(params.stopLat);
		ride.stopLng = strToNumber(params.stopLng);

		ride.startAddress = params.startAddress;
		ride.stopAddress = params.stopAddress;

		User user = User.get(params.user.id)
		Bike bike = Bike.get(params.bike.id)

		if(!user || !bike) {
			response.sendError(400)
			return;
		}

		ride.user = user
		ride.bike = bike

		ride.message = params.message
		ride.lockPassword = params.lockPassword

		if(ride.save()) {

			def photo = request.getFile('photo')
			if(photo.bytes) {
				if (!ImageUtils.saveRidePhotoFromMultipart(servletContext, photo, ride.id)) {
					flash.error = "Could not save photo"
					return render(view:'edit', model: ["ride":params])
				}
			}

			flash.message = "Ride " + params.title + " successfully saved"
			redirect(action: "index")
		}
		else {
			flash.error = "Could not save ride"
			render(view:'edit', model: ["ride": params])
		}*/
	}

	@Transactional
	def add() {
		if(params.id) {
			params.bike = [id : params.id]
			params.id = null
		}
		return this.edit()
	}
	/*
	@Transactional
	def delete() {
		if(!params.id) return sendError(code: 400)
		
		Ride ride = Ride.get(params.id)
		
		if(ride.delete()) {
			flash.error = "Failed to delete ride"
		}
		else {
			flash.message = "Ride deleted"
		}
		redirect(action:"index")
		
	}
	
	private int strToInt(String val, int dflt = 0) {

		try {
			return Integer.parseInt(val)
		}
		catch(e) {
			return dflt
		}
	}

	private double strToNumber(String val, double dflt = 0.0) {

		try {
			return Double.valueOf(val)
		}
		catch(e) {
			return dflt
		}
	}*/
}
