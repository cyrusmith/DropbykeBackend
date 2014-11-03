package com.dropbyke

class Bike {

	static hasMany = [rides: Ride]

	String sku
	String title
	int rating
	int priceRate //cents per hour

	double lat = 0.0
	double lng = 0.0
	String address = 0.0
	String lockPassword = ""
	String messageFromLastUser = ""
	long lastRideId = 0
	
	boolean locked = false

	boolean active = true

	static constraints = {
		sku blank: false, unique: true
	}

}