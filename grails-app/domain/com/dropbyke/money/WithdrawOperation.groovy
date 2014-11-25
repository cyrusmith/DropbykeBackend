package com.dropbyke.money

class WithdrawOperation extends Operation {

    static constraints = {
    }

    @Override
    long changeAccountSum(long sum) {
        return sum - amount
    }
}
