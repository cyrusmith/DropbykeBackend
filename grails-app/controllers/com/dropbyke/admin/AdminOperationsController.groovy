package com.dropbyke.admin

import com.dropbyke.ParseUtils
import com.dropbyke.User
import com.dropbyke.money.Account
import com.dropbyke.money.Operation
import grails.plugin.springsecurity.annotation.Secured

@Secured(["ROLE_ADMIN"])
class AdminOperationsController {

    static allowedMethods = [index: 'GET', withdraw: 'POST']

    def accountService

    def index() {
        List operations
        User user

        if (!params.containsKey("sort")) {
            params["sort"] = "created"
            params["order"] = "desc"
        }

        if (params.userId) {
            session["userId"] = params.userId
            user = User.load(params.userId)
            operations = Operation.findAllByAccount(user.account, params)
        } else if (params.containsKey("sort") && session["userId"]) {
            user = User.load(params.userId)
            operations = Operation.findAllByAccount(user.account, params)
        } else {
            session["userId"] = null
            user = null
            operations = Operation.list(params)
        }

        [operations: operations, opsCount: Operation.count(), user: user]
    }

    def withdraw() {

        if (!params.containsKey("userId") || !params.containsKey("sum")) {
            return render(status: 400, contentType: "application/json") { ["error": "Params not set"] }
        }

        try {
            long amount = ParseUtils.strToLong(params["sum"])
            println "amount=" + amount + " " + params["sum"]
            Account account = accountService.addWidthdraw(params.getLong("userId"), amount)
            return render(status: 200, contentType: "application/json") { account }
        } catch (e) {
            return render(status: 500, contentType: "application/json") { ["error": e.message] }
        }

    }

}