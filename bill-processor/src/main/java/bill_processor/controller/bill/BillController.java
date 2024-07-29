package bill_processor.controller.bill;

import bill_processor.model.bill.Bill;

import java.time.LocalDate;

public class BillController {

    public Bill create(String code, LocalDate date, Double value) {
        return new Bill("code", LocalDate.now(), 0.0);
    }
}
