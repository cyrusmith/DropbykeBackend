package com.dropbyke

class Bike {
	
	static hasMany = [rides: Ride]
	
	String title
	BigDecimal lat
	BigDecimal lon
	
	boolean riding
	
	byte[] photo	
	
	boolean active = true
	
	static constraints = {
		photo(nullable: true, maxSize: 102400)
	  }
  
}
