package com.dropbyke.admin

import com.dropbyke.Bike
import com.dropbyke.Ride
import com.dropbyke.User
import com.dropbyke.money.CheckoutOperation
import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional

@Secured(["ROLE_ADMIN"])
class AdminTestController {

    def cardService
    def accountService

    def testCheckoutStripe() {
        String chargeId = cardService.chargeStripe(["123", "cus_5DC16hLx6dC2aq"], 10000000L, "232323223")
        render chargeId
    }

    @Transactional
    def addRides() {

        if (request.get) {
            return render(view: 'addRides')
        }

        println params

        User user = User.load(params.getLong("userId"))
        Bike bike = Bike.load(params.getLong("bikeId"))

        println user
        println bike

        if (!user || !bike) {
            flash.message = "Error"
            return render(view: 'addRides')
        }

        CheckoutOperation checkoutOperation

        Ride.withTransaction {

            Ride ride = new Ride(
                    bike: bike,
                    user: user,

                    startAddress: params["startAddress"],
                    startTime: params.getLong("startTime"),
                    startLat: params.getDouble("startLat"),
                    startLng: params.getDouble("startLng"),

                    stopAddress: params["stopAddress"],
                    stopTime: params.getLong("stopTime"),
                    stopLat: params.getDouble("stopLat"),
                    stopLng: params.getDouble("stopLng"),

                    message: params["message"],
                    complete: true,
                    charged: false,

                    sum: params.getLong("sum")

            )

            if (!ride.save()) {
                ride.errors.allErrors.each { println it }
                throw new RuntimeException("Failed to save ride")
            }

            println accountService.addCheckout(ride.id, "strp_324238472sdhbf872348")

        }

        redirect(action: 'addRides')


    }

}