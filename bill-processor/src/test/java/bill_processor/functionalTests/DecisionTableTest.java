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

    @Test
    @DisplayName("Should throw exception when trying to pay a bill which date is after invoice date, payment date is after invoice date and payment type is boleto")
    void shouldNotBePossibleToPayWhenBillDateIsAfterInvoiceAndPaymentDateIsAfterBillDate() {
        Invoice invoice = invoiceWithFutureDate.setDate(LocalDate.now().minusDays(2));
        LocalDate date = LocalDate.now().minusDays(1);
        Bill bill = new Bill(invoice, "code", date, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.BOLETO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should throw exception when trying to pay a bill which date is after invoice date and payment type is cartao")
    void shouldNotBePossibleToPayWhenTypeIsCartaoAndBillDateIsNot15DaysBeforeInvoiceDateTest() {
        Invoice invoice = invoiceWithFutureDate.setDate(LocalDate.now().minusDays(4));
        LocalDate invalidDate = invoiceWithFutureDate.getDate().plusDays(10);

        Bill bill = new Bill(invoice, "billCode", invalidDate, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should throw exception when trying to pay a bill when payment date is after bill date and payment type is cartao")
    void shouldNotBePossibleToPayWhenTypeIsCartaoAndPaymentDateIsAfterBillDate() {
        LocalDate invalidDate = invoiceWithFutureDate.getDate().minusDays(10);

        Bill bill = new Bill(invoiceWithFutureDate, "billCode", invalidDate, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should pay bill when type is cartao")
    void shouldPayBillWhenTypeIsCartao() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate().minusDays(15),
                INVOICE_VALUE);

        PaymentTypeEnum paymentType = PaymentTypeEnum.CARTAO_CREDITO;
        LocalDate paymentDate = LocalDate.now();
        Payment expectedPayment = new Payment(invoiceWithFutureDate.getTotalValue(), paymentDate, paymentType);

        Payment payment = billProcessor.payBill(bill, paymentType);

        assertEquals(bill.getPayment(), expectedPayment);
        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should not pay bill when type is cartao and bill date is not 15 days before invoice date")
    void shouldNotPayBillWhenTypeIsCartaoAndBillDateIsNot15DaysBeforeInvoiceDate() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate().minusDays(14),
                INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should not pay bill when type is cartao and bill date is after invoice date")
    void shouldNotPayBillWhenTypeIsCartaoAndBillDateAfterInvoiceDate() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate().plusDays(10),
                INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should pay bill when type is transferencia")
    void shouldPayBillWhenTypeIsTransferenciaTest() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate(),
                INVOICE_VALUE);

        PaymentTypeEnum paymentType = PaymentTypeEnum.TRANSFERENCIA_BANCARIA;
        LocalDate paymentDate = LocalDate.now();
        Payment expectedPayment = new Payment(invoiceWithFutureDate.getTotalValue(), paymentDate, paymentType);

        Payment payment = billProcessor.payBill(bill, paymentType);

        assertEquals(bill.getPayment(), expectedPayment);
        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should pay bill with original value when type is transferencia and payment date is after bill date")
    void shouldPayBillWithOriginalValueWhenTypeIsTransferenciaAndPaymentIsAfterBillDateTest() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate().minusDays(10),
                INVOICE_VALUE);

        PaymentTypeEnum paymentType = PaymentTypeEnum.TRANSFERENCIA_BANCARIA;
        LocalDate paymentDate = LocalDate.now();
        Payment expectedPayment = new Payment(invoiceWithFutureDate.getTotalValue(), paymentDate, paymentType);

        Payment payment = billProcessor.payBill(bill, paymentType);

        assertEquals(bill.getPayment(), expectedPayment);
        assertEquals(expectedPayment, payment);
    }

    @Test
    @DisplayName("Should not pay bill when type is tranferencia and bill date is after invoice date")
    void shouldNotPayBillWhenTypeIsTransferenciaAndBillDateIsAfterInvoiceDate() {
        Bill bill = new Bill(
                invoiceWithFutureDate,
                "billCode",
                invoiceWithFutureDate.getDate().plusDays(10),
                INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        });

        assertNull(bill.getPayment());
    }

}
