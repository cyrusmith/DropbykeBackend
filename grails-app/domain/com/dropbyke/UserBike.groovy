package com.dropbyke

import org.apache.commons.lang.builder.HashCodeBuilder

class UserBike implements Serializable {

	User user
	Bike bike

	boolean active = false

	boolean equals(other) {
		if (!(other instanceof UserRole)) {
			return false
		}

		other.user?.id == user?.id &&
				other.bike?.id == bike?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (bike) builder.append(bike.id)
		builder.toHashCode()
	}
	
	static UserBike create(User user, Bike bike, boolean flush = false) {
		def instance = new UserBike(user: user, bike: bike)
		instance.save(flush: flush, insert: true)
		instance
	}

	static mapping = {
		id composite: ['bike', 'user']
		version false
	}

	static constraints = {
	}
}