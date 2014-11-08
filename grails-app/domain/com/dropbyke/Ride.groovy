package com.dropbyke

class Ride {

	Bike bike
	User user
	long startTime //in millis
	long stopTime //in millis
	
	String startAddress = ""
	double startLat = 0.0
	double startLng = 0.0

	String stopAddress = ""
	double stopLat = 0.0
	double stopLng = 0.0

	String message = ""
	String lockPassword = ""
	boolean hasPhoto = false
	
	boolean complete = false
	
	boolean charged = false

	static hasMany = [paths: Path]

	static constraints = {
	}
}
