package bill_processor;

import bill_processor.exceptions.InvalidBillDataException;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillProcessorTest {

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
    @DisplayName("Should pay bill with success when data is valid")
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
    @DisplayName("Should throw exception when trying to pay a bill which date is after invoice date with every payment type")
    void shouldNotBePossibleToPayWhenBillDateIsAfterInvoiceDate() {
        LocalDate invalidDate = invoiceWithFutureDate.getDate().plusDays(2);

        Bill bill = new Bill(invoiceWithFutureDate, "billCode", invalidDate, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.BOLETO);
        });

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        });

        assertNull(bill.getPayment());
    }

    @Test
    @DisplayName("Should not be possible to pay when payment type is cartao credito and bill date is not at least 15 days before invoice date")
    void shouldNotBePossibleToPayWhenBillHasInvalidDateAndTypeIsCartaoCredito() {
        LocalDate invalidDate = invoiceWithFutureDate.getDate().minusDays(10);

        Bill bill = new Bill(invoiceWithFutureDate, "billCode", invalidDate, INVOICE_VALUE);

        assertThrows(InvalidBillDataException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
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
    @DisplayName("Should process empty list of bills")
    void shouldProcessEmptyListOfBills() {
        List<Bill> bills = new ArrayList<>();

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should process bills updating invoice status when total value was paid")
    void shouldProcessListOfBillsPayingTotalInvoiceValue() {
        Double value = INVOICE_VALUE / 2;
        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), value);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), value);

        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PAGA, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should process bills not updating invoice status when value is not equal to total value")
    void shouldProcessListOfBillsNotPayingTotalInvoiceValue() {
        Double value = INVOICE_VALUE / 2;
        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), value);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), value - 3);

        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should process payments when paid value is bigger than invoice value, updating invoice status")
    void shouldProcessListOfBillsPayingMoreThanTotalInvoiceValue() {
        Double value = INVOICE_VALUE / 2;
        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), value);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), value + 3);

        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PAGA, invoiceWithFutureDate.getStatus());
    }
}
