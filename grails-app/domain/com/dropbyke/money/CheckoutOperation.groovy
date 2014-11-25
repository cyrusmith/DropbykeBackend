package com.dropbyke.money

class CheckoutOperation extends Operation {

    static constraints = {
    }

    String chargeId

    @Override
    long changeAccountSum(long sum) {
        return sum + amount
    }
}
