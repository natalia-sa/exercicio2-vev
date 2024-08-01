package bill_processor.service.bill;

import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;

import java.time.LocalDate;

public class BillService {

    public Bill create(Invoice invoice, String code, LocalDate date, Double value) {
        return new Bill(invoice, code, date, value);
    }
}
