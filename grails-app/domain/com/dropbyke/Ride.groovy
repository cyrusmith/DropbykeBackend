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
	boolean complete = false	
	boolean charged = false
	
	long sum = 0
	long sumCheckout = 0

	int distance = 0

	static hasMany = [paths: Path]

	static constraints = {
	}
}
