package bill_processor.services;

import bill_processor.services.invoice.InvoiceService;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceServiceTest {

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

        InvoiceService invoiceService = new InvoiceService();
        Invoice invoice = invoiceService.create(date, totalValue, customerName);

        assertEquals(expectedInvoice, invoice);
    }
}