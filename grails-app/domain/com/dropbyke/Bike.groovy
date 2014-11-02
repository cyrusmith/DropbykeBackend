package com.dropbyke

class Bike {

	static hasMany = [rides: Ride]

	String sku
	String title
	int rating
	int priceRate //cents per second

	boolean locked = false

	boolean active = true

	static constraints = {
		sku blank: false, unique: true
	}

}