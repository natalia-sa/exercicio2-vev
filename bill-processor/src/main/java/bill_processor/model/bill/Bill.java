package bill_processor.model.bill;

import bill_processor.model.payment.Payment;

import java.time.LocalDate;
import java.util.Objects;

public class Bill {
    private String code;
    private LocalDate date;
    private Double value;
    private Payment payment;

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

    public Payment getPayment() {
        return payment;
    }

    public Bill setCode(String code) {
        this.code = code;
        return this;
    }

    public Bill setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Bill setValue(Double value) {
        this.value = value;
        return this;
    }

    public Bill setPayment(Payment payment) {
        this.payment = payment;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Bill bill = (Bill) o;
        return Objects.equals(code, bill.code) && Objects.equals(date, bill.date) && Objects.equals(value, bill.value) && Objects.equals(payment, bill.payment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, date, value, payment);
    }
}
