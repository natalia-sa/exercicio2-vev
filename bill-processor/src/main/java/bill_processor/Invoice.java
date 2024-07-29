package bill_processor;

import java.time.LocalDate;

public class Invoice {

    private LocalDate date;
    private Double totalValue;
    private String customerName;
    private String status;

    public Invoice(LocalDate date, Double totalValue, String customerName, String status) {
        this.date = date;
        this.totalValue = totalValue;
        this.customerName = customerName;
        this.status = status;
    }
}
