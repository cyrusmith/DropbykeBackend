package com.dropbyke

import com.dropbyke.money.CheckoutOperation
import com.dropbyke.money.Operation
import com.dropbyke.money.WithdrawOperation
import grails.transaction.Transactional
import org.springframework.validation.FieldError

@Transactional
class AccountService {

    def messageSource

    CheckoutOperation addCheckout(long rideId, String chargeId) throws Exception {
        Ride ride = Ride.load(rideId)

        if (!ride) {
            throw new IllegalArgumentException("Ride not found")
        }

        if (ride.charged) {
            throw new IllegalStateException("Ride is already charged")
        }

        if (!ride.complete) {
            throw new IllegalStateException("Ride is not complete yet")
        }

        long amount = ride.sum

        if (amount < 1) {
            throw new IllegalArgumentException("Ride was not complete yet. Cannot charge.")
        }

        User user = User.load(ride.bike.user.id)

        long resultAmount = calcDropbykeFee(amount)

        CheckoutOperation op = Operation.createCheckout(resultAmount, chargeId)

        op.model = Operation.MODEL_RIDE
        op.modelId = ride.id

        op.sumBefore = user.account.sum
        user.account.addOperation(op)
        op.sumAfter = user.account.sum
        if (!user.account.save()) {
            String msg = "Failed to save checkout"
            if (user.account.errors.allErrors.size() > 0) {
                FieldError error = user.account.errors.allErrors.get(0)
                msg = messageSource.getMessage(error, Locale.default)
            }
            throw new Exception(msg)
        }

        ride.charged = true
        ride.sumCheckout = resultAmount
        if (!ride.save()) {
            String msg = "Failed to save checkout"
            if (ride.errors.allErrors.size() > 0) {
                FieldError error = ride.errors.allErrors.get(0)
                msg = messageSource.getMessage(error, Locale.default)
            }
            throw new Exception(msg)
        }

        return op
    }

    def addWidthdraw(long userId, long amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Amount should be greater than zero")
        }
        User user = User.load(userId)
        WithdrawOperation op = Operation.createWithdraw(amount)
        op.sumBefore = user.account.sum
        user.account.addOperation(op)
        op.sumAfter = user.account.sum
        user.account.save()
    }

    private long calcDropbykeFee(long sum) {
        return Math.ceil(sum * 85.0 / 100.0)
    }

}
