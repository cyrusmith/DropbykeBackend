package com.dropbyke

import grails.transaction.Transactional

@Transactional
class BikesService {

    def setActive(long bikeId, boolean isActive) throws Exception {
        Bike bike = Bike.load(bikeId)
        if (!bike) {
            return;
        }
        bike.active = isActive
        if (!bike.save()) {
            def errors = bike.errors.allErrors
            errors.each { it -> println it }
            throw new Exception("Failed to save bike status")
        }
    }

    def startUsage(long userId, long bikeId) {

        Bike bike = Bike.get(bikeId)
        User user = User.get(userId)

        if (!bike) {
            throw new Exception("Bike not found")
        }

        if (!user) {
            throw new Exception("User not found")
        }

        if (bike.locked) {
            throw new Exception("Bike already in use")
        }

        def rc = Ride.createCriteria()
        def rideAlreadyInProgress = rc.list {
            eq('bike', bike)
            eq('stopTime', 0L)
        }

        if (rideAlreadyInProgress && rideAlreadyInProgress.size()) {
            throw new Exception("Bike already has ride in progres")
        }

        rc = Ride.createCriteria()
        rideAlreadyInProgress = rc.list {
            eq('user', user)
            eq('stopTime', 0L)
        }

        if (rideAlreadyInProgress && rideAlreadyInProgress.size()) {
            throw new Exception("You already have ride in progres")
        }

        def startTime = System.currentTimeMillis();

        Ride ride = new Ride(user: user, bike: bike, startTime: startTime, startLat: bike.lat, startLng: bike.lng, startAddress: bike.address)

        if (!ride.save()) {
            throw new Exception("Failed to create ride")
        }

        return ride
    }

    boolean isCloseRule(double dist) {
        return dist > 0.00001 && dist <= 1000.0
    }
}
