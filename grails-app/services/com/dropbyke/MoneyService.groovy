package com.dropbyke

import com.dropbyke.money.Operation
import grails.transaction.Transactional

class MoneyService {

    @Transactional
    void addCheckout(long userId, long amount, String chargeId) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount should be greater than zero")
        }
        User user = User.load(userId)
        long resultAmount = calcDropbykeFee(amount)
        println resultAmount
        user.account.addOperation(Operation.createCheckout(resultAmount, chargeId))
        user.account.save()
    }

    long calcDropbykeFee(long sum) {
        return Math.ceil(sum * 85.0 / 100.0)
    }
}
