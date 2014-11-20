package com.dropbyke

class UserBike implements Serializable {

	User user
	Bike bike
	
	boolean active = false
	
	static mapping = {
		id composite: ['bike', 'user']
		version false
	}
	
    static constraints = {
    
	}
	
}