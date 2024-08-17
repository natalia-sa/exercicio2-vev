package bill_processor.functionalTests;

import bill_processor.BillProcessor;
import bill_processor.exceptions.InvalidBillDataException;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DecisionTableTest {

    private BillProcessor billProcessor;
    private Invoice invoiceWithFutureDate;
    private static final Double INVOICE_VALUE = 30.0;

    @BeforeEach
    void setUp() {
        billProcessor = new BillProcessor();
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        invoiceWithFutureDate = new Invoice(invoiceFutureDate, INVOICE_VALUE, "Daiane dos Santos");
    }

    @Test
    @DisplayName("Should make payment adding 10% to payment value when payment type is boleto and date is bigger than bill date")
    void shouldIncrementPaymentValueIfTypeIsBoletoAndDateIsBiggerThanBillDate() {
        LocalDate billDate = invoiceWithFutureDate.getDate().minusDays(5);
        Bill bill = new Bill(invoiceWithFutureDate, "code", billDate, INVOICE_VALUE);

        Double updatedValue = INVOICE_VALUE + (INVOICE_VALUE * 0.10);
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(updatedValue, date, type);

        Payment payment = billProcessor.payBill(bill, type);

        assertEquals(expectedPayment, payment);
        assertEquals(bill.getPayment(), expectedPayment);
    }

    @Test
    @DisplayName("Should pay bill with original value with success when data is valid")
    void shouldPayBillWhenDataIsValid() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate(),
                INVOICE_VALUE);

        PaymentTypeEnum paymentType = PaymentTypeEnum.BOLETO;
        LocalDate paymentDate = LocalDate.now();
        Payment expectedPayment = new Payment(invoiceWithFutureDate.getTotalValue(), paymentDate, paymentType);

        Payment payment = billProcessor.payBill(bill, paymentType);

        assertEquals(bill.getPayment(), expectedPayment);
        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should throw exception when trying to pay a bill which date is after invoice date and payment type is boleto")
    void shouldNotBePossibleToPayWhenBillDateIsAfterInvoiceDate() {
        LocalDate invalidDate = invoiceWithFutureDate.getDate().plusDays(2);

        Bill bill = new Bill(invoiceWithFutureDate, "billCode", invalidDate, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.BOLETO);
        });

        assertNull(bill.getPayment());
    }
}
