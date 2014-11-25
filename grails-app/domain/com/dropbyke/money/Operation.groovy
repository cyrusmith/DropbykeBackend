package com.dropbyke.money

abstract class Operation {

    static belongsTo = [account: Account]

    long amount

    static createCheckout(long amount, String chargeId) {
        return new CheckoutOperation(amount: amount, chargeId: chargeId)
    }

    static createWithdraw(long amount) {
        return new WithdrawOperation(amount: amount)
    }

    abstract long changeAccountSum(long sum)

    static constraints = {
    }
}
