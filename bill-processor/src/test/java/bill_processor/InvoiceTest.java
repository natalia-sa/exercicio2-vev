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
        String status = "PENDENTE";

        Invoice expectedInvoice = new Invoice(date, totalValue, customerName, status);

        InvoiceController invoiceController = new InvoiceController();
        Invoice invoice = invoiceController.create();

        assertEquals(expectedInvoice, invoice);
    }
}
