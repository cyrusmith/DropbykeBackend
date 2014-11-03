package com.dropbyke.admin

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dropbyke.Bike;
import com.dropbyke.ImageUtils;
import com.dropbyke.ParseUtils;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

@Secured(["permitAll"])
class AdminBikesController {

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
		bike.locked = ParseUtils.strToInt(params.locked)
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
}
