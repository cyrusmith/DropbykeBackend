package com.dropbyke.admin

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.dropbyke.Bike;
import com.dropbyke.ImageUtils;

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
					sku:bike.sku
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
