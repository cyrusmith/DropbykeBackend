package com.dropbyke.admin

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dropbyke.Bike;
import com.dropbyke.ImageUtils;
import com.dropbyke.ParseUtils;
import com.dropbyke.Ride;
import com.dropbyke.User;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

@Secured(["ROLE_ADMIN"])
class AdminBikesController {

	def servletContext

	def index() {
		[bikes: Bike.list(params), bikesCount: Bike.count()]
	}

	@Transactional
	def edit() {

		if(request.get) {
			if(params.id) {
				Bike bike = Bike.get(params.id)
				if(!bike) {
					return response.sendError(404)
				}
				return render(view:'edit', model: [
					id:bike.id,
					title:bike.title,
					sku:bike.sku,
					address:bike.address,
					lat:bike.lat,
					lng:bike.lng,
					priceRate:bike.priceRate,
					locked:bike.locked,
					lockPassword:bike.lockPassword,
					messageFromLastUser:bike.messageFromLastUser
				])
			}
			return render(view:'edit')
		}

		Bike bike
		if(params.id) {
			bike = Bike.get(params.id)
		}
		else {
			bike = new Bike()
		}

		if(bike.locked) {
			flash.error = "Cannot edit locked bike. Unlock first."
			return render(view:'edit', model: [
				id:bike.id,
				title:bike.title,
				sku:bike.sku,
				address:bike.address,
				lat:bike.lat,
				lng:bike.lng,
				priceRate:bike.priceRate,
				locked:bike.locked,
				lockPassword:bike.lockPassword,
				messageFromLastUser:bike.messageFromLastUser
			])
		}

		if(!params.sku) {
			flash.error = "SKU not set"
			return render(view:'edit', model: [
				title:params.title,
				sku:params.sku
			])
		}

		bike.title = params.title
		bike.sku = params.sku
		bike.priceRate = ParseUtils.strToInt(params.priceRate)
		bike.lat = ParseUtils.strToNumber(params.lat)
		bike.lng = ParseUtils.strToNumber(params.lng)
		bike.address = params.address
		bike.sku = params.sku
		bike.lockPassword = params.lockPassword
		bike.messageFromLastUser = params.messageFromLastUser

		System.out.println params

		if(bike.save()) {
			flash.message = "Bike " + params.title + " successfully saved"
			redirect(action: "index")
		}
		else {
			flash.error = "Could not save bike"
			return render(view:'edit', model: [
				title:params.title,
				sku:params.sku
			])
		}
	}

	@Transactional
	def add() {
		return this.edit()
	}

	@Transactional
	def stopUsage() {

		Bike bike = Bike.get(params.id)

		if(!bike.locked) {
			flash.error = "Bike is already unlocked"
			redirect(action: "index")
		}

		def rc = Ride.createCriteria()
		Ride ride = rc.get {
			eq('bike', bike)
			eq('complete', false)
		}

		User user = User.get(ride.user.id)

		if(!ride) {
			flash.error = "Bike has no ride in progress"
			redirect(action: "index")
		}

		if(request.get) {
			return render(view:'stopUsage', model: [
				ride: ride,
				bike: bike,
				user: user
			])
		}

		List errors = []

		if(!params.stopAddress) {
			errors.add "Address not set"
		}

		if(!params.stopTime) {
			errors.add "Stop timestamp not set"
		}

		if(!params.stopLat || !params.stopLng) {
			errors.add "Location not set"
		}

		if(!params.lockPassword) {
			errors.add "Lock password not set"
		}

		if(!params.message) {
			errors.add "Message not set"
		}

		def photo = request.getFile('photo')
		if(photo.bytes) {
			if (!ImageUtils.saveRidePhotoFromMultipart(servletContext, photo, ride.id)) {
				errors.add "Could not save photo"
			}
		}
		else {
			errors.add "Photo not set"
		}

		String stopAddress = params.stopAddress
		double stopLat = ParseUtils.strToNumber(params.stopLat)
		double stopLng = ParseUtils.strToNumber(params.stopLng)
		long stopTime = ParseUtils.strToLong(params.stopTime)*1000
		String message = params.message
		String lockPassword = params.lockPassword

		if(Math.abs(stopLat) < 0.00001 || Math.abs(stopLng) < 0.00001) {
			errors.add "Location not set"
		}

		if(!stopTime) {
			errors.add "Stop time not set"
		}

		System.out.println stopTime + " " + ride.startTime

		if(stopTime < ride.startTime) {
			errors.add "Stop time should be greater than start time"
		}

		if(errors.size() > 0) {
			flash.error = errors.join(", ")
			return render(view:'stopUsage', model: [
				ride: [
					id: ride.id,
					startTime: ride.startTime,
					startAddress: ride.startAddress,
					startLat: ride.startLat,
					startLng:ride.startLng,
					stopTime: params.stopTime,
					stopAddress: params.stopAddress,
					stopLat:params.stopLat,
					stopLng:params.stopLng
				],
				bike: [
					id: bike.id,
					title: bike.title,
					lockPassword:params.lockPassword,
					messageFromLastUser: params.message
				],
				user: user
			])
		}

		ride.complete = true
		ride.stopAddress = stopAddress
		ride.stopLat = stopLat
		ride.stopLng = stopLng
		ride.stopTime = stopTime
		ride.message = message
		ride.lockPassword = lockPassword
		ride.lockPassword = true

		bike.locked = false
		bike.lat = stopLat
		bike.lng = stopLng
		bike.address = stopAddress
		bike.lockPassword = lockPassword
		bike.messageFromLastUser = message
		bike.lastUserPhone = user.phone
		bike.lastRideId = ride.id

		if(ride.save() && bike.save()) {
			return redirect(action: "edit", id: bike.id)
		}
	}
}
