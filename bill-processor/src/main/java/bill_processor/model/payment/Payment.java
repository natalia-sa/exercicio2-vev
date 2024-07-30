package bill_processor.model.payment;

import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {

    private Double value;
    private LocalDate date;
    private PaymentTypeEnum type;

    public Payment(Double value, LocalDate date, PaymentTypeEnum type) {
        this.value = value;
        this.date = date;
        this.type = type;
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
}
