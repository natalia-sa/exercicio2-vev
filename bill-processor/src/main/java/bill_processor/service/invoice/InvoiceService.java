package bill_processor.service.invoice;

import bill_processor.model.invoice.Invoice;

import java.time.LocalDate;

public class InvoiceService {

    public Invoice create(LocalDate date, Double totalValue, String customerName) {
        return new Invoice(date, totalValue, customerName);
    }
}
