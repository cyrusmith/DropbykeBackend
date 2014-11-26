package com.dropbyke.admin

import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AdminTestController {

    def cardService
    def accountService

    def testCheckoutStripe() {
        String chargeId = cardService.chargeStripe(["123", "cus_5DC16hLx6dC2aq"], 10000000L, "232323223")
        render chargeId
    }

    def testMoneyService() {
        String chargeId = "chchchchch"
        render accountService.addCheckout(2L, 100L, chargeId)
    }

}
