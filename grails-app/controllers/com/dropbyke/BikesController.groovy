package com.dropbyke

import java.text.NumberFormat;

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.hibernate.criterion.CriteriaSpecification;

import grails.plugin.springsecurity.annotation.Secured;


class BikesController {

	@Secured(["permitAll"])
	def avatarImage() {
		def bike = Bike.get(params.id)
		if (!bike || !bike.photo) {
			response.sendError(404)
			return
		}
		response.contentType = bike.photoType
		response.contentLength = bike.photo.size()
		OutputStream out = response.outputStream
		out.write(bike.photo)
		out.close()
	}

	@Secured(["permitAll"])
	def bikesInArea() {

		NumberFormat formatter = NumberFormat.getNumberInstance(Locale.US)

		double lat1 = params["lat1"]?formatter.parse(params["lat1"]):0.0
		double lat2 = params["lat2"]?formatter.parse(params["lat2"]):0.0
		double lng1 = params["lng1"]?formatter.parse(params["lng1"]):0.0
		double lng2 = params["lng2"]?formatter.parse(params["lng2"]):0.0

		def bikes

		def c = Bike.createCriteria()

		if((Math.abs(lng1 - lng2) > 0.0000001) && Math.abs(lat1 - lat2) > 0.0000001) {
			bikes = c.list(sort:"title", order: "asc") {
				maxResults 20
				gt('lat', lat1)
				lt('lat', lat2)
				gt('lon',lng1)
				lt('lon', lng2)
				eq('riding', false)
				projections {
					property('id')
					property('title')
					property('lat')
					property('lon')
				}
			}
		}
		else {
			bikes = c.list(max: 20) {
				maxResults 20
				eq('riding', false)
				projections {
					property('id')
					property('title')
					property('lat')
					property('lon')
				}
			}
		}

		render(status: 200, contentType: "application/json") {
			["bikes": bikes?bikes.collect {
					[id: it[0], title: it[1], lat: it[2], lng: it[3]]
				}:[]]
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