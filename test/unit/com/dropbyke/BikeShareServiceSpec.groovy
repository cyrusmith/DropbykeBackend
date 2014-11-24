
package com.dropbyke

import grails.test.mixin.TestFor
import spock.lang.Specification
import grails.test.GrailsMock;
/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BikeShareService)
@grails.test.mixin.Mock([User, Bike])
class BikeShareServiceSpec extends Specification {

	def setup() {
	}

	def cleanup() {
	}

	void "test addBike"() {
		given:
		GrailsMock userMock = mockFor(User)
		userMock.demand.static.get() {Long id -> new User(id: id,username: "username", password: "password")}

		when:

		def bike = service.addBike(2L, "sss", "nnn", 5, 55.1943714, 61.2803689, "улица Бейвеля, 8А, Челябинск, Челябинская область, Россия, 454021", "qwe", "Yfg", false)
		then:
		bike instanceof Bike
		bike.sku == "sss"
		bike.lockPassword == "qwe"
	}

	void "test addBike existing sku"() {
		given:
		GrailsMock userMock = mockFor(User)
		User user = new User(id: 2L,username: "username", password: "password")
		userMock.demand.static.get() {Long id -> user}

		when:
		new Bike(
				user: user,
				sku: "sss",
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

	void "test userBikesInArea"() {
		given:
		
		User user = new User(id: 2L,username: "username", password: "password")
		
		Bike bike = new Bike(
			user: user,
			active: true,
			sku: "sss1",
			title:"name",
			priceRate:5,
			lat:1.99,
			lng:1.77,
			address: "address",
			lockPassword: "lockPassword",
			messageFromLastUser:"message")
		
		bike.save(flush: true)
		
		new Bike(
			user: user,
			sku: "sss2",
			title:"name",
			priceRate:5,
			lat:1.99,
			lng:1.77,
			address: "address",
			lockPassword: "lockPassword",
			messageFromLastUser:"message").save(flush : true)
		
		GrailsMock userMock = mockFor(User)
		userMock.demand.static.get() {Long id -> user}

		when:

		//long userId, double lat1, double lng1, double lat2, double lng2
		def bikes = service.userBikesInArea(2L, 1.00, 1.00, 2.00, 2.00)

		then:
		bikes instanceof List
		bikes.size() == 1
		bikes.get(0).id == bike.id
	}
}
