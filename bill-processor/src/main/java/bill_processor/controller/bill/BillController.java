package bill_processor.controller.bill;

import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;

import java.time.LocalDate;

public class BillController {

    public Bill create(Invoice invoice, String code, LocalDate date, Double value) {
        return new Bill(invoice, code, date, value);
    }
}
