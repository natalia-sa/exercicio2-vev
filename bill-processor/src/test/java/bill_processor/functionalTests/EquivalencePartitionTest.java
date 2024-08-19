package bill_processor.functionalTests;

import bill_processor.BillProcessor;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EquivalencePartitionTest {

    private BillProcessor billProcessor;

    @BeforeEach
    void setUp() {
        billProcessor = new BillProcessor();
    }

    @Test
    @DisplayName("Should process bills and should not update bill status when total value was not paid")
    void shouldProcessListOfBillsNotPayingTotalInvoiceValueTest() {
        Double invoiceValue = 1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), 300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), 300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should process bills and update bill status when total value was paid")
    void shouldProcessListOfBillsAndUpdateBillStatusTest() {
        Double invoiceValue = 1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), 500.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), 500.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PAGA, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should process bills and should not accept negative values")
    void shouldProcessListOfBillsAndShouldNotAcceptNegativeValues() {
        Double invoiceValue = 1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), -300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), 300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        billProcessor.payBill(bill2, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        assertThrows(Exception.class,
                ()->{
                    billProcessor.processBills(bills);
                });

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when bill value is invalid")
    void shouldThrowExceptionWhenBillValueIsInvalidTest() {
        Double invoiceValue = -1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), -300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), 300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        billProcessor.payBill(bill2, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        assertThrows(Exception.class,
                ()->{
                    billProcessor.processBills(bills);
                });

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when bill and invoice values are invalid")
    void shouldThrowExceptionWhenBillAndInvoiceValueAreInvalidTest() {
        Double invoiceValue = -1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), -300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), -300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        billProcessor.payBill(bill2, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        assertThrows(Exception.class,
                ()->{
                    billProcessor.processBills(bills);
                });

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when all invoice values are invalid")
    void shouldThrowExceptionWhenAllValuesAreInvalidTest() {
        Double invoiceValue = 1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), -300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), -300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        billProcessor.payBill(bill2, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        assertThrows(Exception.class,
                ()->{
                    billProcessor.processBills(bills);
                });

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }

    @Test
    @DisplayName("Should throw exception when bill and one invoice value are invalid")
    void shouldThrowExceptionWhenAllBillAndOneInvoiceValueAreInvalidTest() {
        Double invoiceValue = -1000.0;
        LocalDate invoiceFutureDate = LocalDate.now().plusDays(2);
        Invoice invoiceWithFutureDate = new Invoice(invoiceFutureDate, invoiceValue, "Daiane dos Santos");

        Bill bill1 = new Bill(invoiceWithFutureDate, "code", LocalDate.now(), 300.0);
        Bill bill2 = new Bill(invoiceWithFutureDate, "code2", LocalDate.now(), -300.0);

        billProcessor.payBill(bill1, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        billProcessor.payBill(bill2, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        assertThrows(Exception.class,
                ()->{
                    billProcessor.processBills(bills);
                });

        assertEquals(InvoiceStatusEnum.PENDENTE, invoiceWithFutureDate.getStatus());
    }
}
