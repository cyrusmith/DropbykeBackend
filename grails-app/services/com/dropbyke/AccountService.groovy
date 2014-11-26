package com.dropbyke

import com.dropbyke.money.CheckoutOperation
import com.dropbyke.money.Operation
import grails.transaction.Transactional

@Transactional
class AccountService {


    def addCheckout(long userId, long amount, String chargeId) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount should be greater than zero")
        }
        User user = User.load(userId)
        long resultAmount = calcDropbykeFee(amount)
        println resultAmount
        CheckoutOperation op = Operation.createCheckout(resultAmount, chargeId)
        op.sumBefore = user.account.sum
        user.account.addOperation(op)
        op.sumAfter = user.account.sum
        user.account.save()
    }

    private long calcDropbykeFee(long sum) {
        return Math.ceil(sum * 85.0 / 100.0)
    }

}
