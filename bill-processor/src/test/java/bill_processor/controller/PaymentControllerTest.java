package bill_processor.controller;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class PaymentControllerTest {

    @Test
    public void shouldCreatePayment() {
        Double value = 2.0;
        LocalDate date = LocalDate.now();
        String type = "BOLETO";

        Payment expectedPayment = new Payment(value, date, type);
        PaymentController paymentController = new PaymentController();
        Payment payment = paymentController.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }
}
