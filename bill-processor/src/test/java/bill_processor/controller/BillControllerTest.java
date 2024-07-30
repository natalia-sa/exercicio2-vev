package bill_processor.controller;

import bill_processor.controller.bill.BillController;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BillControllerTest {

    @Test
    void shouldCreateBill() {
        Invoice invoice = new Invoice(LocalDate.now(), 30.0, "name");
        String code = "code";
        LocalDate date = LocalDate.now();
        Double value = 30.0;

        Bill expectedBill = new Bill(invoice, code, date, value);

        BillController billController = new BillController();
        Bill bill = billController.create(invoice, code, date, value);

        assertEquals(expectedBill, bill);
        assertNull(expectedBill.getPayment());
    }
}
