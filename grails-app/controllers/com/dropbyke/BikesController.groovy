package com.dropbyke

import java.text.NumberFormat;

import javax.transaction.Transaction;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.hibernate.criterion.CriteriaSpecification;

import grails.plugin.springsecurity.annotation.Secured;
import groovy.sql.Sql
import groovy.sql.GroovyRowResult

class BikesController {

	def dataSource

	@Secured(['permitAll'])
	def bikesInArea() {

		double lat1 = 0.0
		double lat2 = 0.0
		double lng1 = 0.0
		double lng2 = 0.0

		lat1 = ParseUtils.strToNumber(params["lat1"], 0.0)
		lat2 = ParseUtils.strToNumber(params["lat2"], 0.0)
		lng1 = ParseUtils.strToNumber(params["lng1"], 0.0)
		lng2 = ParseUtils.strToNumber(params["lng2"], 0.0)

		List queryResults

		if((Math.abs(lng1 - lng2) > 0.0000001) && Math.abs(lat1 - lat2) > 0.0000001) {
			String query = $/
			select b.id, b.title, b.sku, r.stop_lat, r.stop_lng
			from bike as b
			left join ride as r on b.id=r.bike_id
			where 0 = (select count(id) from ride as rd where rd.bike_id = b.id and rd.stop_time = 0) and
			stop_lat > :lat1 and stop_lat < :lat2 and
			stop_lng > :lng1 and stop_lng < :lng2
			order by b.id, r.stop_time desc
			/$

			Sql sql = new Sql(dataSource)

			final results = sql.rows(query, lat1: lat1, lat2:lat2, lng1:lng1, lng2:lng2)

			queryResults = results
		}
		else {

			String query = $/
			select b.id, b.title, b.sku, r.stop_lat, r.stop_lng
			from bike as b
			left join ride as r on b.id=r.bike_id
			where 0 = (select count(id) from ride as rd where rd.bike_id = b.id and rd.stop_time = 0)
			order by b.id, r.stop_time desc
			/$

			Sql sql = new Sql(dataSource)

			final results = sql.rows(query)

			queryResults = results
		}

		List bikes = []
		
		long lastId = 0
		for(def res in queryResults) {
			if(res.id == lastId) {
				continue
			}
			lastId = res.id
			bikes.add(res)
		}
		
		System.out.println "bikes=" + bikes

		render(status: 200, contentType: "application/json") { ["bikes": bikes] }
	}

	@Secured(['ROLE_USER'])
	def view() {
		System.out.println "params.id=" + params.id
		if(!params.id) {
			return render(status: 404, contentType:"application/json") { ["error": "ID not set"] }
		}

		def id = params.id.toLong()

		def c = Bike.createCriteria()
		def bike = c.get {
			eq('id', id)
			projections {
				property('id')
				property('title')
				property('sku')
				property('lat')
				property('lon')
			}
		}

		if(!bike) {
			return render(status: 404, contentType:"application/json") { ["error": "Not found"] }
		}

		return render(status: 200, contentType:"application/json") {
			["bike": [
					id: bike[0], title: bike[1], sku: bike[2], lat: bike[3], lng: bike[4]
				]]
		}
	}

	/**
	 * Using haversine formula - http://en.wikipedia.org/wiki/Haversine_formula
	 * @return
	 */
	private Map calcGeoBoundsFromSphereDistance(double lat, double lon, double dist) {

		def R = 6371000.0 //Earth radius
		def D = 2.0*R //Earth diam

		lat = lat*Math.PI/180
		lon = lon*Math.PI/180

		def deltaLng = 2.0*asin(sin(dist/D)/cos(lat))
		def deltaLat = dist/R

	}
}