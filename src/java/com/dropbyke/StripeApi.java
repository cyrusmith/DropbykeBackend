package com.dropbyke;

import java.util.HashMap;
import java.util.Map;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;

public class StripeApi {

	public static Charge charge(String customerId, long amount, String phone,
			String email, String name) {

		Map<String, Object> chargeParams = new HashMap<String, Object>();

		chargeParams.put("amount", amount);
		chargeParams.put("currency", "usd");
		chargeParams.put("customer", customerId);
		chargeParams.put("description", "Charge for test@example.com");

		Map<String, Object> initialMetadata = new HashMap<String, Object>();
		initialMetadata.put("phone", phone);
		initialMetadata.put("email", email);
		initialMetadata.put("name", name);

		chargeParams.put("metadata", initialMetadata);

		try {
			return Charge.create(chargeParams);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());	
		}

	}
}