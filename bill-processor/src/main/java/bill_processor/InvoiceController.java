package bill_processor;

import java.time.LocalDate;

public class InvoiceController {

    public Invoice create() {
        return new Invoice(LocalDate.now(), 0.0, "name", "PENDENTE");
    }
}
