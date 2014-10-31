package com.dropbyke

class Bike {
	
	static hasMany = [rides: Ride]
	
	String sku
	String title
	double lat
	double lon
	
	boolean riding = false
	
	byte[] photo
	
	String photoType = ""	
	
	boolean active = true
	
	static constraints = {
		sku blank: false, unique: true
		photo(nullable: true, maxSize: 102400)
	  }
  
}
