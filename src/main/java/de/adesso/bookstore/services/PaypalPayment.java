package de.adesso.bookstore.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * The service for PayPal payments.
 */
@Slf4j
@Service
@Profile("paypal")
public class PaypalPayment implements PaymentService {

    /**
     * Pay a given amount using PayPal payment.
     *
     * @param amount The amount to pay
     */
    @Override
    public void pay(double amount) {
        log.info("pay {} with Paypal.", amount);
    }
}
