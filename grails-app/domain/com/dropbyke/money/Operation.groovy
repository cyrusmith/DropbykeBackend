package com.dropbyke.money

abstract class Operation {

    private static final Date NULL_DATE = new Date(0)

    static belongsTo = [account: Account]

    long amount
    long sumBefore
    long sumAfter

    Date created = NULL_DATE

    def beforeInsert() {
        if (created == NULL_DATE) {
            created = new Date()
        }
    }

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
