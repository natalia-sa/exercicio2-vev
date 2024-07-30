package bill_processor;

import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BillProcessorTest {

    @Test
    void shouldPayBillWhenDataIsValid() {
        LocalDate invoiceDate = LocalDate.now().plusDays(2);
        Double invoiceValue = 30.0;
        String customerName = "Daiane dos Santos";
        Invoice invoice = new Invoice(invoiceDate, invoiceValue, customerName);

        String billCode = "code";
        Bill bill = new Bill(invoice, billCode, invoiceDate, invoiceValue);

        PaymentTypeEnum paymentType = PaymentTypeEnum.BOLETO;
        LocalDate paymentDate = LocalDate.now();
        Payment expectedPayment = new Payment(invoiceValue, paymentDate, paymentType);

        BillProcessor billProcessor = new BillProcessor();
        Payment payment = billProcessor.payBill(bill, paymentType);

        assertEquals(bill.getPayment(), expectedPayment);
        assertEquals(expectedPayment, payment);
    }

    @Test
    void shouldNotBePossibleToPayWhenBillDateIsAfterInvoiceDate() {
        LocalDate invoiceDate = LocalDate.now().minusDays(2);
        Double invoiceValue = 30.0;
        String customerName = "Daiane dos Santos";
        Invoice invoice = new Invoice(invoiceDate, invoiceValue, customerName);

        LocalDate billDate = LocalDate.now().plusDays(1);
        String billCOde = "code";

        Bill bill = new Bill(invoice, billCOde, billDate, invoiceValue);

        BillProcessor billProcessor = new BillProcessor();

        assertThrows(IllegalArgumentException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.BOLETO);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertThrows(IllegalArgumentException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.TRANSFERENCIA_BANCARIA);
        });

        assertNull(bill.getPayment());
    }

    // Bill date be at least 15 days before invoice date
    @Test
    void shouldNotBePossibleToPayWhenBillHasInvalidDateAndTypeIsCartaoCredito() {
        LocalDate invoiceDate = LocalDate.now();
        Double invoiceValue = 30.0;
        String customerName = "Daiane dos Santos";
        Invoice invoice = new Invoice(invoiceDate, invoiceValue, customerName);

        String billCode = "code";
        LocalDate billDate = invoiceDate.minusDays(10);
        Bill bill = new Bill(invoice, billCode, billDate, invoiceValue);

        BillProcessor billProcessor = new BillProcessor();

        assertThrows(IllegalArgumentException.class, ()->{
            billProcessor.payBill(bill, PaymentTypeEnum.CARTAO_CREDITO);
        });

        assertNull(bill.getPayment());
    }

    @Test
    void shouldIncrementPaymentValueIfTypeIsBoletoAndDateIsBiggerThanBillDate() {
        LocalDate invoiceDate = LocalDate.now();
        Double invoiceValue = 30.0;
        String customerName = "Daiane dos Santos";
        Invoice invoice = new Invoice(invoiceDate, invoiceValue, customerName);

        String code = "code";
        LocalDate billDate = invoiceDate.minusDays(2);
        Bill bill = new Bill(invoice, code, billDate, invoiceValue);

        Double value = invoiceValue + (invoiceValue * 0.10);
        LocalDate date = LocalDate.now();
        PaymentTypeEnum type = PaymentTypeEnum.BOLETO;

        Payment expectedPayment = new Payment(value, date, type);

        BillProcessor billProcessor = new BillProcessor();
        Payment payment = billProcessor.payBill(bill, type);

        assertEquals(expectedPayment, payment);
        assertEquals(bill.getPayment(), expectedPayment);
    }

    @Test
    void shouldProcessEmptyListOfBills() {
        Invoice invoice = new Invoice(LocalDate.now(), 30.0, "customer");

        List<Bill> bills = new ArrayList<>();
        BillProcessor billProcessor = new BillProcessor();

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PENDENTE, invoice.getStatus());
    }

    @Test
    void shouldProcessListOfBillsPayingTotalInvoiceValue() {
        Invoice invoice = new Invoice(LocalDate.now().plusDays(2), 30.0, "customer");

        Bill bill1 = new Bill(invoice, "code", LocalDate.now(), 15.0);
        Bill bill2 = new Bill(invoice, "code2", LocalDate.now(), 15.0);

        BillProcessor billProcessor = new BillProcessor();
        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PAGA, invoice.getStatus());
    }

    @Test
    void shouldProcessListOfBillsNotPayingTotalInvoiceValue() {
        Invoice invoice = new Invoice(LocalDate.now().plusDays(2), 30.0, "customer");

        Bill bill1 = new Bill(invoice, "code", LocalDate.now(), 15.0);
        Bill bill2 = new Bill(invoice, "code2", LocalDate.now(), 14.0);

        BillProcessor billProcessor = new BillProcessor();
        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PENDENTE, invoice.getStatus());
    }

    @Test
    void shouldProcessListOfBillsPayingMoreThanTotalInvoiceValue() {
        Invoice invoice = new Invoice(LocalDate.now().plusDays(2), 30.0, "customer");

        Bill bill1 = new Bill(invoice, "code", LocalDate.now(), 15.0);
        Bill bill2 = new Bill(invoice, "code2", LocalDate.now(), 16.0);

        BillProcessor billProcessor = new BillProcessor();
        billProcessor.payBill(bill1, PaymentTypeEnum.BOLETO);
        billProcessor.payBill(bill2, PaymentTypeEnum.BOLETO);

        List<Bill> bills = new ArrayList<>();
        bills.add(bill1);
        bills.add(bill2);

        billProcessor.processBills(bills);

        assertEquals(InvoiceStatusEnum.PAGA, invoice.getStatus());
    }
}
