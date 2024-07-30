package bill_processor.model.bill;

import bill_processor.model.payment.Payment;

import java.time.LocalDate;
import java.util.Objects;

public class Bill {

    private String code;
    private LocalDate date;
    private Double value;

    public Bill(String code, LocalDate date, Double value) {
        this.code = code;
        this.date = date;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(code, bill.code) && Objects.equals(date, bill.date) && Objects.equals(value, bill.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, date, value);
    }
}
