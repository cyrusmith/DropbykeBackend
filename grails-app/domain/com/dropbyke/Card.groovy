package com.dropbyke

class Card {

	String number
	String name
	String expire
	String cvc	
	String stripeCustomerId
	
	static belongsTo = [user: User]
	
    static constraints = {
    
	}
	
}