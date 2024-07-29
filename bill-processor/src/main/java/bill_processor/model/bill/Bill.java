package bill_processor.model.bill;

import java.time.LocalDate;

public class Bill {

    private String code;
    private LocalDate date;
    private Double value;

    public Bill(String code, LocalDate date, Double value) {
        this.code = code;
        this.date = date;
        this.value = value;
    }
}
