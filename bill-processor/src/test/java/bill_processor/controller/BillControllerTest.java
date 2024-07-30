package bill_processor.controller;

import bill_processor.controller.bill.BillController;
import bill_processor.controller.payment.PaymentController;
import bill_processor.model.bill.Bill;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillControllerTest {

    @Test
    void shouldCreateBill() {
        String code = "code";
        LocalDate date = LocalDate.now();
        Double value = 30.0;

        Bill expectedBill = new Bill(code, date, value);

        BillController billController = new BillController();
        Bill bill = billController.create(code, date, value);

        assertEquals(expectedBill, bill);
    }

    @Test
    void shouldIncrementPaymentValueIfTypeIsBoletoAndDateIsBiggerThanBillDate() {
        String code = "code";
        LocalDate billDate = LocalDate.of(2024, 1, 1);
        Double billValue = 30.0;
        Bill bill = new Bill(code, billDate, billValue);

        Double value = 33.0;
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);

        BillController billController = new BillController();
        Payment payment = billController.pay(bill, type);

        assertEquals(expectedPayment, payment);
    }
}
