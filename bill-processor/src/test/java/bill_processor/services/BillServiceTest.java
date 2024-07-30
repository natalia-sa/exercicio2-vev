package bill_processor.services;

import bill_processor.services.bill.BillService;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BillServiceTest {

    @Test
    void shouldCreateBill() {
        Invoice invoice = new Invoice(LocalDate.now(), 30.0, "name");
        String code = "code";
        LocalDate date = LocalDate.now();
        Double value = 30.0;

        Bill expectedBill = new Bill(invoice, code, date, value);

        BillService billService = new BillService();
        Bill bill = billService.create(invoice, code, date, value);

        assertEquals(expectedBill, bill);
        assertNull(expectedBill.getPayment());
    }
}
