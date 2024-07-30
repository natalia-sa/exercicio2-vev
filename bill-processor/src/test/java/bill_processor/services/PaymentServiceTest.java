package bill_processor.services;

import bill_processor.services.payment.PaymentService;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceTest {

    @Test
    void shouldCreatePayment() {
        Double value = 2.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        PaymentService paymentService = new PaymentService();
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    void shouldThrowExceptionWhenTypeIsBoletoAndValueIsInferiorToMinimum() {
        Double value = 0.001;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        PaymentService paymentService = new PaymentService();

        assertThrows(IllegalArgumentException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }

    @Test
    void shouldThrowExceptionWhenTypeIsBoletoAndValueIsBiggerThenMaximum() {
        Double value = 5001.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        PaymentService paymentService = new PaymentService();

        assertThrows(IllegalArgumentException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }


}
