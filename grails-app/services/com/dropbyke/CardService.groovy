package com.dropbyke

import com.stripe.Stripe
import com.stripe.exception.CardException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import grails.transaction.Transactional
import groovy.util.logging.Log4j;

@Log4j
class CardService {

    def grailsApplication
    def accountService

    @Transactional
    def editCard(long userId, String number, String name, String expire, String cvc, String stripeCustomerId) {

        User user = User.get(userId)

        if (!user) {
            throw new IllegalArgumentException("User not found")
        }

        Card card = user.cards.find { it.number == number }

        if (!card) {
            card = new Card(number: number, name: name, expire: expire, cvc: cvc, stripeCustomerId: stripeCustomerId)

            user.addToCards(card).save()
        } else {
            card.number = number
            card.name = name
            card.expire = expire
            card.cvc = cvc
            card.stripeCustomerId = stripeCustomerId
            card.save()
        }

        return card
    }

    def createCustomer(String cardNumber, String name, String expireMonth, String expireYear, String cvc) {

        log.debug("Create customer with: {" + cardNumber + ", " + name + ", " + cvc + "}")

        Stripe.apiKey = grailsApplication.config.com.dropbyke.stripeApiKey;

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", "Customer for " + name);

        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", cardNumber);
        cardParams.put("name", name);
        cardParams.put("exp_month", expireMonth);
        cardParams.put("exp_year", expireYear);
        cardParams.put("cvc", cvc);

        customerParams.put("card", cardParams);

        try {
            return Customer.create(customerParams)
        }
        catch (CardException e) {
            log.error(e.message)
            return e.message
        }
        catch (StripeException e) {
            log.error(e)
            return null
        }
    }

    def checkout(long userId, long rideId, long amount) throws Exception {

        Stripe.apiKey = grailsApplication.config.com.dropbyke.stripeApiKey;

        if (amount < 50) {
            throw new IllegalArgumentException("Amount is too small");
        }

        User user
        Ride ride

        Ride.withTransaction {
            user = User.get(userId)
            ride = Ride.get(rideId)
        }

        if (!user) {
            throw new IllegalArgumentException("Could not find user");
        }

        if (!ride) {
            throw new IllegalArgumentException("Could not find ride");
        }

        if (ride.charged) {
            throw new IllegalStateException("Ride already charged");
        }

        if (user.cards.isEmpty()) {
            throw new IllegalStateException("User has no cards");
        }

        List stripeIds = []

        for (Card card in user.cards) {
            stripeIds.add(card.stripeCustomerId)
        }

        String stripeChargeId = chargeStripe(stripeIds, amount, user.phone)
        println stripeChargeId
        if (stripeChargeId) {
            accountService.addCheckout(ride.id, stripeChargeId)
        }

    }

    String chargeStripe(List stripeIds, long amount, String userphone) {

        Stripe.apiKey = grailsApplication.config.com.dropbyke.stripeApiKey;

        for (String stripeId in stripeIds) {

            try {
                Map<String, Object> chargeParams = new HashMap<String, Object>();

                chargeParams.put("amount", amount);
                chargeParams.put("currency", "usd");
                chargeParams.put("customer", stripeId);
                chargeParams.put("description", "Charge for " + userphone);

                Map<String, Object> initialMetadata = new HashMap<String, Object>();
                initialMetadata.put("phone", userphone)
                chargeParams.put("metadata", initialMetadata)

                Charge charge = Charge.create(chargeParams)

                if (charge.failureCode) {
                    continue;
                }

                return charge.id

            }
            catch (StripeException e) {
                println e.message
                continue;
            }
        }

        return null

    }

}