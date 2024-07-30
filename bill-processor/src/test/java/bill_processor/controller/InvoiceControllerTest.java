package bill_processor.controller;

import bill_processor.controller.invoice.InvoiceController;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceControllerTest {

    @Test
    void shouldCreateInvoice() {
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
