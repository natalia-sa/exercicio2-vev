package bill_processor.model.payment;

import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {

    private Double value;
    private LocalDate date;
    private PaymentTypeEnum type;

    public Payment() {
    }

    public Payment(Double value, LocalDate date, PaymentTypeEnum type) {
        this.value = value;
        this.date = date;
        this.type = type;
    }

    public Payment setValue(Double value) {
        this.value = value;
        return this;
    }

    public Payment setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Payment setType(PaymentTypeEnum type) {
        this.type = type;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public LocalDate getDate() {
        return date;
    }

    public PaymentTypeEnum getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(value, payment.value) && Objects.equals(date, payment.date) && Objects.equals(type, payment.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date, type);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "value=" + value +
                ", date=" + date +
                ", type=" + type +
                '}';
    }
}
