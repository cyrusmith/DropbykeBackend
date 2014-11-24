package com.dropbyke

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BikeShareService)
@grails.test.mixin.Mock([User, Bike, UserBike])
class BikeShareServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test addBike"() {
		when:
		def bike = service.addBike(2L, "sss", "nnn", 5, 55.1943714, 61.2803689, "улица Бейвеля, 8А, Челябинск, Челябинская область, Россия, 454021", "qwe", "Yfg", false)
		then:
		bike instanceof Bike
		bike.sku == "sss"
		bike.lockPassword == "qwe"
	}

	void "test addBike existing sku"() {
		when:
		new Bike(sku: "sss",
		title:"name",
		priceRate:5,
		lat:1.99,
		lng:2.77,
		address: "address",
		lockPassword: "lockPassword",
		messageFromLastUser:"message").save(flush: true)

		def msg = shouldFail {
			def bike = service.addBike(2L, "sss", "nnn", 5, 55.1943714, 61.2803689, "улица Бейвеля, 8А, Челябинск, Челябинская область, Россия, 454021", "qwe", "Yfg", false)
		}

		then:
		msg == "Serial is not unique"
	}

	void "test setUserBikeActive"() {
		when:
		UserBike.metaClass.static.findByBikeAndUser = { Bike bike, User user ->
			return new UserBike(bike:  bike, user: user)
		}
		def ret = service.setUserBikeActive(1L, 2L, false)
		then:
		ret
	}
}
