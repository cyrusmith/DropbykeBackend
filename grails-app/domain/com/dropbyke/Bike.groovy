package com.dropbyke

class Bike {
	
	static hasMany = [rides: Ride]
	
	String title
	double lat
	double lon
	
	boolean riding = false
	
	byte[] photo
	
	String photoType = ""	
	
	boolean active = true
	
	static constraints = {
		photo(nullable: true, maxSize: 102400)
	  }
  
}
