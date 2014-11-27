package com.dropbyke.money

import com.dropbyke.User

class Account {

    static belongsTo = [user: User]

    static hasMany = [operations: Operation]

    long sum

    def addOperation(Operation op) throws MoneyException {
        long newSum = op.changeAccountSum(sum)
        if (newSum < 0) {
            throw new MoneyException(message: "Not enough money to perform this operation")
        }
        sum = newSum
        this.addToOperations(op)
        this
    }

    static constraints = {
        sum validator: { if (it < 0) return ['invalid.sum'] }
    }
}
