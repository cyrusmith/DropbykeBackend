package com.dropbyke

class Bike {

	static hasMany = [rides: Ride]
	
	static belongsTo = User
	User user
	
	String sku
	String title
	double rating
	int priceRate

	double lat = 0.0
	double lng = 0.0
	String address = 0.0
	String lockPassword = ""
	String messageFromLastUser = ""
	String lastUserPhone = ""
	long lastRideId = 0
	
	boolean locked = false

	boolean active = false

	static constraints = {
		sku blank: false, unique: true
	}

}