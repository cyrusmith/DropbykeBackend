package com.dropbyke

class Charge {

	User user
	Ride ride
	long amount	
	String cardNumber
	long timestamp
	String stripeChargeId
	
    static constraints = {
    }
}
