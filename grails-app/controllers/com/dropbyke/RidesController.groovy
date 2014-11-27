package com.dropbyke

import org.codehaus.groovy.grails.web.json.JSONObject;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;
import grails.validation.ValidationException
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.springframework.web.multipart.MultipartFile;

class RidesController {

    def springSecurityService
    def ridesService
    def fileUploadService

    @Secured('ROLE_USER')
    def stopRide() {

        def authenticatedUser = springSecurityService.loadCurrentUser()

        JSONObject data = request.JSON

        def lat = data.has("lat") ? ParseUtils.strToNumber(data.getString("lat"), 0.0) : 0.0
        def lng = data.has("lng") ? ParseUtils.strToNumber(data.getString("lng"), 0.0) : 0.0
        def address = data.has("address") ? data.getString("address") : ""
        def message = data.has("message") ? data.get("message") : ""
        def distance = data.has("distance") ? ParseUtils.strToInt(data.get("distance")) : 0

        if (!message) {
            message = ""
        }

        try {
            def result = ridesService.stopRide(authenticatedUser.id, lat, lng, address, message, distance)
            return render(status: 200, contentType: "application/json") { result }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") {
                [
                        "error": e.message
                ]
            }
        }
    }

    @Secured('ROLE_USER')
    @Transactional
    def listOwnersRides() {

        User authUser = springSecurityService.loadCurrentUser()

        BuildableCriteria rc = Ride.createCriteria()
        List rides = rc.list {
            'in' 'bike', authUser.bikes
            eq('charged', true)
            eq('complete', true)
            order('stopTime', 'desc')
        }

        List filtered = []

        rides.each { Ride ride ->
            filtered.add([
                    "id"       : ride.id,
                    "startTime": ride.startTime,
                    "startLat" : ride.startLat,
                    "startLng" : ride.startLng,
                    "stopTime" : ride.stopTime,
                    "stopLat"  : ride.stopLat,
                    "stopLng"  : ride.stopLng,
                    "bikeTitle": ride.bike.title,
                    "sum"      : ride.sum
            ])
        }

        render(status: 200, contentType: "application/json") { filtered }
    }

    @Secured('ROLE_USER')
    @Transactional
    def viewOwnersRide() {

        if (!params.containsKey("id")) {
            render(status: 404, contentType: "application/json") {}
        }

        User authUser = springSecurityService.loadCurrentUser()

        BuildableCriteria rc = Ride.createCriteria()
        Ride ride = rc.get {
            eq('id', params.getLong("id"))
            'in' 'bike', authUser.bikes
            eq('charged', true)
            eq('complete', true)
            order('stopTime', 'desc')
        }
        Map result
        if (ride) {
            result = [
                    "id"          : ride.id,
                    "startTime"   : ride.startTime,
                    "startAddress": ride.startAddress,
                    "startLat"    : ride.startLat,
                    "startLng"    : ride.startLng,
                    "stopTime"    : ride.stopTime,
                    "stopAddress" : ride.stopAddress,
                    "stopLat"     : ride.stopLat,
                    "stopLng"     : ride.stopLng,
                    "bikeTitle"   : ride.bike.title,
                    "sum"         : ride.sumCheckout
            ]

        } else {
            result = []
        }

        render(status: 200, contentType: "application/json") { result }
    }

    @Secured('ROLE_USER')
    @Transactional
    def viewRide() {

        def authenticatedUser = springSecurityService.loadCurrentUser()
        long rideId = ParseUtils.strToNumber(params.id)

        def rc = Ride.createCriteria()

        def ride = rc.get {
            eq('id', rideId)
            eq('user', authenticatedUser)
        }

        if (!ride) {
            return render(status: 404, contentType: "application/json")
        }

        Bike bike = Bike.get(ride.bike.id)

        return render(status: 200, contentType: "application/json") {
            [
                    'ride': ride,
                    'bike': bike
            ]
        }
    }

    @Secured('ROLE_USER')
    def uploadPhoto() {

        User authenticatedUser = springSecurityService.loadCurrentUser()
        MultipartFile photo = request.getFile('photo')

        println "Ride::uploadPhoto"
        println photo

        Ride ride = ridesService.getUserCurrentRide(authenticatedUser.id)

        if (!ride) {
            return render(status: 500, contentType: "application/json") { ["error": "Could not find ride"] }
        }

        if (photo && !photo.isEmpty()) {
            try {
                fileUploadService.savePhoto(photo, FileUploadService.Folder.RIDES, ride.id)
                return render(status: 200, contentType: "application/json") {}
            }
            catch (e) {
                return render(status: 500, contentType: "application/json") {
                    ["error": "Failed to save uploaded file: " + e.message]
                }
            }
        }
        return render(status: 400, contentType: "application/json") { ["error": "No file"] }
    }

    @Secured('ROLE_USER')
    def checkout() {

        def authenticatedUser = springSecurityService.loadCurrentUser()

        JSONObject data = request.JSON

        long rideId = data.has("rideId") ? data.getLong("rideId") : 0L;
        int rating = data.has("rating") ? data.getInt("rating") : 0;

        if (!rideId) {
            return render(status: 400, contentType: "application/json") { ["error": "Ride id not set"] }
        }

        try {
            ridesService.checkout(rideId, authenticatedUser.id, rating)
            return render(status: 200, contentType: "application/json") {}
        }
        catch (ValidationException e) {
            return render(status: 400, contentType: "application/json") { ["error": e.message] }
        }
        catch (Exception e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }
    }
}
