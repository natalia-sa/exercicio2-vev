package bill_processor.controller;

import bill_processor.controller.payment.PaymentController;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentControllerTest {

    @Test
    void shouldCreatePayment() {
        Double value = 2.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        PaymentController paymentController = new PaymentController();
        Payment payment = paymentController.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    void shouldThrowExceptionWhenTypeIsBoletoAndValueIsInferiorToMinimum() {
        Double value = 0.001;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        PaymentController paymentController = new PaymentController();

        assertThrows(IllegalArgumentException.class,
                ()->{
                   paymentController.create(value, date, type);
                });
    }
}
