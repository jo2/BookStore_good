package de.adesso.bookstore.services;

import nl.altindag.log.LogCaptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test for class DummyPayment.
 */
@ExtendWith(MockitoExtension.class)
class DummyPaymentTest {

    /**
     * LogCaptor to capture console output.
     */
    private final LogCaptor logCaptor = LogCaptor.forClass(DummyPayment.class);

    /**
     * Parameterized test to check multiple amounts at once.
     *
     * @param amount The amount to test
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 10.0, 100.0, 49.99})
    void logInfoAndWarnMessages(double amount) {
        final DummyPayment dummyPayment = new DummyPayment();
        dummyPayment.pay(amount);

        Assertions.assertThat(logCaptor.getInfoLogs()).contains("pay " + amount + " with Dummy.");
    }
}
