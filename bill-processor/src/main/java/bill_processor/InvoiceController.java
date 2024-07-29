package bill_processor;

import java.time.LocalDate;

public class InvoiceController {

    public Invoice create(LocalDate date, Double totalValue, String customerName) {
        return new Invoice(date, totalValue, customerName);
    }
}
