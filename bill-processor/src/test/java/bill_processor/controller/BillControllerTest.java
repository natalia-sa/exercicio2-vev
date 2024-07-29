package bill_processor.controller;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class BillControllerTest {

    @Test
    public void shouldCreateBill() {
        String code = "code";
        LocalDate date = LocalDate.now();
        Double value = 30.0;

        Bill expectedBill = new Bill(code, date, value);

        BillController billController = new BillController();
        Bill bill = billController.create(code, date, value);

        assertEquals(expectedBill, bill);
    }
}
