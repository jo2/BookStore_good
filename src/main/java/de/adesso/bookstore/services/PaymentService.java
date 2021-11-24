package de.adesso.bookstore.services;

/**
 * Interface for payment.
 */
public interface PaymentService {

    /**
     * Pay a given amount.
     *
     * @param amount The amount to pay
     */
    void pay(double amount);
}
