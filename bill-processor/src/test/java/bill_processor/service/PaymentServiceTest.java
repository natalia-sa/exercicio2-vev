package bill_processor.service;

import bill_processor.exceptions.InvalidPaymentDataException;
import bill_processor.service.payment.PaymentService;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentServiceTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }

    @Test
    @DisplayName("Should create a payment with the correct data")
    void shouldCreatePayment() {
        Double value = 2.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should throw exception when payment type is boleto and value to pay is inferior to minimum")
    void shouldThrowExceptionWhenTypeIsBoletoAndValueIsInferiorToMinimum() {
        Double value = 0.001;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        assertThrows(InvalidPaymentDataException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }

    @Test
    @DisplayName("Should throw exception when payment type is boleto and value is bigger than maximum")
    void shouldThrowExceptionWhenTypeIsBoletoAndValueIsBiggerThenMaximum() {
        Double value = 5001.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        assertThrows(InvalidPaymentDataException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }

}
