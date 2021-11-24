package de.adesso.bookstore.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * The service for dummy payments.
 */
@Slf4j
@Service
@Profile("dummy")
public class DummyPayment implements PaymentService {

    /**
     * Pay a given amount using dummy payment.
     *
     * @param amount The amount to pay
     */
    @Override
    public void pay(double amount) {
        log.info("pay {} with Dummy.", amount);
    }
}
