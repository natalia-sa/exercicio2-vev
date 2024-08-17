package bill_processor.functionalTests;

import bill_processor.exceptions.InvalidPaymentDataException;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import bill_processor.service.payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LimitValueAnalysisTest {

    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        paymentService = new PaymentService();
    }


    @Test
    @DisplayName("Should create a payment when type is boleto and value is equal to minimum")
    void shouldVerifyLimitsTest1() {
        Double value = 0.01;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should create a payment when type is boleto and value is equal to maximum")
    void shouldVerifyLimitsTest2() {
        Double value = 5000.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should create a payment when type is boleto and value is bigger than minimum")
    void shouldVerifyLimitsTest3() {
        Double value = 0.02;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should create a payment when type is boleto and value is lower than maximum")
    void shouldVerifyLimitsTest4() {
        Double value = 4999.9;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should create a payment when type is boleto and value is random valid value")
    void shouldVerifyLimitsTest5() {
        Double value = 3000.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);
        Payment payment = paymentService.create(value, date, type);

        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should throw exception when payment type is boleto and value to pay is inferior to minimum")
    void shouldVerifyLimitsTest6() {
        Double value = -0.01;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        assertThrows(InvalidPaymentDataException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }

    @Test
    @DisplayName("Should throw exception when payment type is boleto and value to pay is bigger than maximum")
    void shouldVerifyLimitsTest7() {
        Double value = 5001.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        assertThrows(InvalidPaymentDataException.class,
                ()->{
                    paymentService.create(value, date, type);
                });
    }
}
