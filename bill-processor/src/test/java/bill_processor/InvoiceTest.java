package bill_processor;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class InvoiceTest {

    @Test
    public void shouldCreateInvoice() {
        LocalDate date = LocalDate.now();
        Double totalValue = 10.0;
        String customerName = "Daiane dos Santos";
        InvoiceStatusEnum status = InvoiceStatusEnum.PENDENTE;

        Invoice expectedInvoice = new Invoice()
                .setDate(date)
                .setCustomerName(customerName)
                .setTotalValue(totalValue)
                .setStatus(status);

        InvoiceController invoiceController = new InvoiceController();
        Invoice invoice = invoiceController.create(date, totalValue, customerName);

        assertEquals(expectedInvoice, invoice);
    }
}
