package com.dropbyke

class Ride {
	
	Bike bike
	User user
		
	static hasMany = [paths: Path]
	
    static constraints = {
    }
}
