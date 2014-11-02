package com.dropbyke

class Ride {
	
	Bike bike
	User user
	long startTime //in millis
	long stopTime //in millis
	String message
	String lockPassword
	
	String startAddress = ""
	double startLat
	double startLng
	
	String stopAddress = ""
	double stopLat
	double stopLng
		
	static hasMany = [paths: Path]
	
    static constraints = {
    }
}
