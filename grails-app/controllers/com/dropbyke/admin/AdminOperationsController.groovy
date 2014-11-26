package com.dropbyke.admin

import com.dropbyke.User
import com.dropbyke.money.Operation

class AdminOperationsController {

    def index() {
        List operations
        User user
        if (params.userId) {
            session["userId"] = params.userId
            user = User.load(params.userId)
            operations = Operation.findAllByAccount(user.account, params)
        } else if(params.containsKey("sort") && session["userId"]) {
            user = User.load(params.userId)
            operations = Operation.findAllByAccount(user.account, params)
        }
        else {
            session["userId"] = null
            user = null
            operations = Operation.list(params)
        }
        [operations: operations, opCount: Operation.count(), user: user]
    }
}
