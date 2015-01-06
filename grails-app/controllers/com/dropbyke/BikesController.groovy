package com.dropbyke

import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.springframework.validation.FieldError

import org.codehaus.groovy.grails.web.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.dropbyke.FileUploadService.Folder;

import grails.plugin.springsecurity.annotation.Secured;
import grails.transaction.Transactional;

class BikesController {

    def bikesService
    def springSecurityService
    def bikeShareService
    def fileUploadService

    @Secured(['ROLE_USER'])
    def bikesInArea() {

        User authUser = springSecurityService.loadCurrentUser()

        double lat1 = ParseUtils.strToNumber(params["lat1"], 0.0)
        double lat2 = ParseUtils.strToNumber(params["lat2"], 0.0)
        double lng1 = ParseUtils.strToNumber(params["lng1"], 0.0)
        double lng2 = ParseUtils.strToNumber(params["lng2"], 0.0)

        double userLat = ParseUtils.strToNumber(params["userLat"], 0.0)
        double userLng = ParseUtils.strToNumber(params["userLng"], 0.0)

        List bikes
        BuildableCriteria bc = Bike.createCriteria()

        //ne('user', authUser)

        if ((Math.abs(lng1 - lng2) > 0.0000001) && Math.abs(lat1 - lat2) > 0.0000001) {

            bikes = bc.list {
                maxResults(20)
                gt('lat', lat1)
                lt('lat', lat2)
                gt('lng', lng1)
                lt('lng', lng2)
                eq('locked', false)
                eq('active', true)
            }
        } else {
            bikes = []
        }

        long minDist = Long.MAX_VALUE

        if (bikes.size() > 0) {
            bikes.each { Bike bike ->
                double d = dist(bike.lat, bike.lng, userLat, userLng)
                if (d < minDist) {
                    minDist = d
                }
            }
        } else {
            minDist = -1
        }

        render(status: 200, contentType: "application/json") {
            ["bikes": bikes, "nearest": minDist, "location": [userLat, userLng], "user": ["id": authUser.id]]
        }

    }

    @Secured(['ROLE_USER'])
    @Transactional
    def view() {
        User authUser = springSecurityService.loadCurrentUser()
        if (!params.id) {
            return render(status: 404, contentType: "application/json") { ["error": "ID not set"] }
        }

        Long id = params.id.toLong()

        Bike bike = Bike.get(id)

        if (!bike) {
            return render(status: 404, contentType: "application/json") { ["error": "Not found"] }
        }

        if (bike.locked) {
            return render(status: 400, contentType: "application/json") { ["error": "Bike is locked"] }
        }

        def rc = Ride.createCriteria()
        def rides = rc.list {
            maxResults(1)
            gt('stopTime', 0L)
            order("stopTime", "desc")
        }

        def ride = null;
        if (rides && rides.size()) {
            ride = rides.get(0)
        }

        return render(status: 200, contentType: "application/json") {
            ["bike": bike, "ride": ride, "user": ["id": authUser.id, "cards": authUser.cards]]
        }
    }

    @Secured(['ROLE_USER'])
    def isValidDistance() {

        long bikeId = params.getLong('bikeId', 0L)
        double lat = params.getDouble('lat', 0.0)
        double lng = params.getDouble('lng', 0.0)

        Bike bike = Bike.get(bikeId)

        if (!bike) {
            return render(contentType: "application/json", code: 400) {
                [error: "Bike not found"]
            }
        }

        if (bike.locked) {
            return render(contentType: "application/json", code: 400) {
                [error: "Could not get bike location. Bike is in use"]
            }
        }

        return render(contentType: "application/json", code: 200) {
            ["isValidDistance": bikesService.isCloseRule(dist(bike.lat, bike.lng, lat, lng))]
        }

    }

    @Secured(['ROLE_USER'])
    @Transactional
    def startUsage() {
        User authenticatedUser = springSecurityService.loadCurrentUser()

        JSONObject data = request.JSON

        long bikeId = data.getLong("bikeId")
        if (!bikeId) {
            return render(status: 404, contentType: "application/json") { ["error": "Id not set"] }
        }

        if(!authenticatedUser.cards.size()) {
            return render(status: 400, contentType: "application/json") {
                ["error": "Cannot access bike. You have no payment cards."]
            }
        }

        double userLat = data.getDouble("userLat")
        double userLng = data.getDouble("userLng")

        Bike bike = Bike.get(bikeId)

        if (!bike) {
            return render(status: 404, contentType: "application/json") { ["error": "Bike not found"] }
        }

        if (bike.user.id == authenticatedUser.id) {
            return render(status: 404, contentType: "application/json") { ["error": "Cannot use bike added by you"] }
        }

        if (!bikesService.isCloseRule(dist(bike.lat, bike.lng, userLat, userLng))) {
            return render(status: 400, contentType: "application/json") {
                ["error": "Cannot access bike. You are too far."]
            }
        }

        try {
            Ride ride = bikesService.startUsage(authenticatedUser.id, bikeId)
            bike.locked = true
            bike.save()
            return render(status: 200, contentType: "application/json") {
                ["ride": [
                        'id'          : ride.id,
                        'title'       : bike.title,
                        'rating'      : bike.rating,
                        'startTime'   : ride.startTime,
                        'startLat'    : bike.lat,
                        'startLng'    : bike.lng,
                        'price'       : bike.priceRate,
                        'lockPassword': bike.lockPassword,
                        'message'     : bike.messageFromLastUser,
                        'lastRideId'  : bike.lastRideId,
                        'hasPhoto'    : false
                ]]
            }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }
    }

    @Secured(['ROLE_USER'])
    def bikesList() {
        User authUser = springSecurityService.loadCurrentUser()

        double lat1
        double lat2
        double lng1
        double lng2

        lat1 = ParseUtils.strToNumber(params["lat1"])
        lat2 = ParseUtils.strToNumber(params["lat2"])
        lng1 = ParseUtils.strToNumber(params["lng1"])
        lng2 = ParseUtils.strToNumber(params["lng2"])

        try {
            List bikes = bikeShareService.userBikesInArea(authUser.id, lat1, lng1, lat2, lng2)
            return render(status: 200, contentType: "application/json") { ["bikes": bikes] }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }
    }

    @Secured(['ROLE_USER'])
    def bikeInfo() {
        Bike bike = Bike.get(params.id)
        if (!bike) {
            return render(status: 404, contentType: "application/json") { ["error": "Bike not found"] }
        }
        return render(status: 200, contentType: "application/json") { ["bike": bike] }
    }

    @Secured(['ROLE_USER'])
    def addBike() {

        User authUser = springSecurityService.loadCurrentUser()

        JSONObject json = request.JSON

        boolean active
        String name
        String sku
        int price
        String lockPassword
        String address
        double lat
        double lng
        String message

        if (!json.isEmpty()) {
            if (!(json.has("active") &&
                    json.has("title") &&
                    json.has("sku") &&
                    json.has("priceRate") &&
                    json.has("lockPassword") &&
                    json.has("address") &&
                    json.has("lat") &&
                    json.has("lng") &&
                    json.has("messageFromLastUser"))) {
                return render(status: 400, contentType: "application/json") { ["error": "Some fields are not set"] }
            }

            active = json.getBoolean("active")
            name = json.getString("title")
            sku = json.getString("sku")
            price = json.getInt("priceRate")
            lockPassword = json.getString("lockPassword")
            address = json.getString("address")
            lat = json.getDouble("lat")
            lng = json.getDouble("lng")
            message = json.getString("messageFromLastUser")
        } else {
            if (!(params.containsKey("active") &&
                    params.containsKey("title") &&
                    params.containsKey("sku") &&
                    params.containsKey("priceRate") &&
                    params.containsKey("lockPassword") &&
                    params.containsKey("address") &&
                    params.containsKey("lat") &&
                    params.containsKey("lng") &&
                    params.containsKey("messageFromLastUser"))) {
                return render(status: 400, contentType: "application/json") { ["error": "Some fields are not set"] }
            }

            active = params["active"] && true
            name = params["title"]
            sku = params["sku"]
            price = ParseUtils.strToInt(params["priceRate"])
            lockPassword = params["lockPassword"]
            address = params["address"]
            lat = ParseUtils.strToNumber(params["lat"])
            lng = ParseUtils.strToNumber(params["lng"])
            message = params["messageFromLastUser"]
        }

        MultipartFile photo = params.containsKey("photo") ? params["photo"] : null

        if ((!photo || photo.isEmpty() || !fileUploadService.validatePhoto(photo)) && !grailsApplication.config.com.dropbyke.debug) {
            return render(status: 400, contentType: "application/json") { ["error": "Photo not set"] }
        }

        try {
            Bike bike = bikeShareService.addBike(authUser.id, sku, name, price, lat, lng, address, lockPassword, message)

            if (grailsApplication.config.com.dropbyke.debug && (!photo || photo.isEmpty())) {
                return render(status: 200, contentType: "application/json") { ["bike": bike] }
            } else {
                if (fileUploadService.savePhoto(photo, Folder.BIKES, bike.id)) {
                    bikesService.setActive(bike.id, active)
                    return render(status: 200, contentType: "application/json") { ["bike": bike] }
                } else {
                    return render(status: 500, contentType: "application/json") { ["error": "Failed to save image"] }
                }

            }

        }
        catch (IllegalArgumentException e) {
            return render(status: 400, contentType: "application/json") {
                ["error": e.message]
            }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") {
                ["error": e.message]
            }
        }
    }


    @Secured(['ROLE_USER'])
    def editBike() {

        User authUser = springSecurityService.loadCurrentUser()

        JSONObject json = request.JSON

        long id
        boolean active
        String name
        String sku
        int price
        String lockPassword
        String address
        double lat
        double lng

        if (!json.isEmpty()) {
            if (!(json.has("id") &&
                    json.has("active") &&
                    json.has("title") &&
                    json.has("sku") &&
                    json.has("priceRate") &&
                    json.has("lockPassword") &&
                    json.has("address") &&
                    json.has("lat") &&
                    json.has("lng"))) {
                return render(status: 400, contentType: "application/json") { ["error": "Some fields are not set"] }
            }

            id = json.getLong("id")
            active = json.getBoolean("active")
            name = json.getString("title")
            sku = json.getString("sku")
            price = json.getInt("priceRate")
            lockPassword = json.getString("lockPassword")
            address = json.getString("address")
            lat = json.getDouble("lat")
            lng = json.getDouble("lng")
        } else {
            if (!(params.containsKey("active") &&
                    params.containsKey("title") &&
                    params.containsKey("sku") &&
                    params.containsKey("priceRate") &&
                    params.containsKey("lockPassword") &&
                    params.containsKey("address") &&
                    params.containsKey("lat") &&
                    params.containsKey("lng"))) {
                return render(status: 400, contentType: "application/json") { ["error": "Some fields are not set"] }
            }

            id = ParseUtils.strToLong(params["id"])
            active = params["active"] && true
            name = params["title"]
            sku = params["sku"]
            price = ParseUtils.strToInt(params["priceRate"])
            lockPassword = params["lockPassword"]
            address = params["address"]
            lat = ParseUtils.strToNumber(params["lat"])
            lng = ParseUtils.strToNumber(params["lng"])
        }

        if (!id) {
            return render(status: 400, contentType: "application/json") { ["error": "Bike id not set"] }
        }

        try {
            Bike bike = bikeShareService.editBike(authUser.id, id, sku, name, price, lat, lng, address, lockPassword, active)
            if (params.containsKey('photo') && params.photo instanceof MultipartFile) {
                MultipartFile photo = params.photo
                if (photo && !photo.isEmpty() || !fileUploadService.validatePhoto(photo)) {
                    if (!fileUploadService.savePhoto(photo, Folder.BIKES, bike.id)) {
                        return render(status: 500, contentType: "application/json") {
                            ["error": "Failed to save image"]
                        }
                    }
                }
            }

            return render(status: 200, contentType: "application/json") { ["bike": bike] }
        }
        catch (e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }

    }

    @Secured(['ROLE_USER'])
    def addUserPhoto() {

        User authUser = springSecurityService.loadCurrentUser()

        if (!params.containsKey("id")) {
            return render(status: 400, contentType: "application/json") { ["error": "bike id not set"] }
        }

        Bike bike = Bike.findByUserAndId(authUser, params.getLong("id"))

        if (!bike) {
            return render(status: 400, contentType: "application/json") { ["error": "Bike not found"] }
        }

        if (fileUploadService.checkPhotoExists(Folder.BIKEFIRSTUSER, bike.id)) {
            return render(status: 400, contentType: "application/json") { ["error": "Bike already has default image"] }
        }

        MultipartFile photo = request.getFile("photo")

        if (fileUploadService.savePhoto(photo, Folder.BIKEFIRSTUSER, bike.id) || grailsApplication.config.com.dropbyke.debug) {
            return render(status: 200, contentType: "application/json") {
                [
                        "bike": bike
                ]
            }
        } else {
            return render(status: 500, contentType: "application/json") { ["error": "Filed to save user photo"] }
        }

    }

    /**
     * Using haversine formula - http://en.wikipedia.org/wiki/Haversine_formula
     * @return
     */
    private double dist(double lat1, double lng1, double lat2, double lng2) {

        def R = 6371000.0 //Earth radius

        lat1 = lat1 * Math.PI / 180
        lng1 = lng1 * Math.PI / 180
        lat2 = lat2 * Math.PI / 180
        lng2 = lng2 * Math.PI / 180

        double sinLat2 = Math.pow(Math.sin(0.5 * (lat2 - lat1)), 2)
        double sinLng2 = Math.pow(Math.sin(0.5 * (lng2 - lng1)), 2)

        2.0 * R * Math.asin(Math.sqrt(sinLat2 + sinLng2 * Math.cos(lat1) * Math.cos(lat2)))

    }
}