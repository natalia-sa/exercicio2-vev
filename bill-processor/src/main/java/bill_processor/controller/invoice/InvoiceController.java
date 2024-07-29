package bill_processor.controller.invoice;

import bill_processor.model.invoice.Invoice;

import java.time.LocalDate;

public class InvoiceController {

    public Invoice create(LocalDate date, Double totalValue, String customerName) {
        return new Invoice(date, totalValue, customerName);
    }
}
