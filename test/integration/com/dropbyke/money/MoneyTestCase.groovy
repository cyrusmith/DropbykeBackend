package com.dropbyke.money

/**
 * Created by cyrusmith on 25.11.2014.
 */
class MoneyTestCase extends GroovyTestCase  {

    void testCheckout() {
        CheckoutOperation checkout = Operation.createCheckout(10L)
        Account acc = new Account(sum: 20)
        acc.addOperation(checkout)
        acc.sum == 10L
    }
}
