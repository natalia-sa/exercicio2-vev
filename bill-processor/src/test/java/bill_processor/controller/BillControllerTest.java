package bill_processor.controller;

import bill_processor.controller.bill.BillController;
import bill_processor.model.bill.Bill;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BillControllerTest {

    @Test
    void shouldCreateBill() {
        String code = "code";
        LocalDate date = LocalDate.now();
        Double value = 30.0;

        Bill expectedBill = new Bill(code, date, value);

        BillController billController = new BillController();
        Bill bill = billController.create(code, date, value);

        assertEquals(expectedBill, bill);
    }
}
