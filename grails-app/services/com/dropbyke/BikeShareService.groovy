package com.dropbyke

import grails.transaction.Transactional
import org.springframework.validation.FieldError

@Transactional
class BikeShareService {

    def messageSource

    def addBike(long userId, String sku, String name, int price, double lat, double lng, String address, String lockPassword, String message) throws Exception {

        User user = User.get(userId)

        if (!user) {
            throw new IllegalArgumentException("User not exists")
        }

        if (!(name && sku && price && lockPassword && address && message)) {
            throw new IllegalArgumentException("Some fields are not set")
        }

        Bike bike = new Bike(
                user: user,
                sku: sku,
                title: name,
                priceRate: price,
                lat: lat,
                lng: lng,
                address: address,
                lockPassword: lockPassword,
                messageFromLastUser: message)

        if (!bike.save()) {
            String msg = "Failed to save bike"
            if (bike.errors.allErrors.size() > 0) {
                FieldError error = bike.errors.allErrors.get(0)
                msg = messageSource.getMessage(error, Locale.default)
            }
            throw new Exception(msg)
        }

        return bike
    }

    def editBike(long userId, long bikeId, String sku, String name, int price, double lat, double lng, String address, String lockPassword, boolean active) throws Exception {

        if (!(name && sku && price && lockPassword && address)) {
            throw new IllegalArgumentException("Some fields are not set")
        }

        Bike bike = Bike.findByUserAndId(User.load(userId), bikeId)

        if(!bike) {
            throw new IllegalArgumentException("Bike not found")
        }

        if(bike.locked) {
            throw new Exception("Bike is in use")
        }

        bike.sku = sku
        bike.title = name
        bike.priceRate = price
        bike.lat = lat
        bike.lng = lng
        bike.active = active
        bike.address = address
        bike.lockPassword = lockPassword

        if (!bike.save()) {
            String msg = "Failed to save bike"
            if (bike.errors.allErrors.size() > 0) {
                FieldError error = bike.errors.allErrors.get(0)
                msg = messageSource.getMessage(error, Locale.default)
            }
            throw new Exception(msg)
        }

        return bike
    }

    def userBikesInArea(long userId, double lat1, double lng1, double lat2, double lng2) throws Exception {

        User user = User.get(userId)

        if (!user) {
            throw new IllegalArgumentException("User not found")
        }

        def bc = Bike.createCriteria()

        def bikes = bc.list {
            maxResults(20)
            eq('user', user)
            gt('lat', lat1)
            lt('lat', lat2)
            gt('lng', lng1)
            lt('lng', lng2)
        }

        return bikes

    }

}