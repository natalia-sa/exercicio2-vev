package bill_processor.model.bill;

import bill_processor.model.invoice.Invoice;
import bill_processor.model.payment.Payment;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
public class Bill {
    private Invoice invoice;
    private String code;
    private LocalDate date;
    private Double value;
    private Payment payment;

    public Bill(Invoice invoice, String code, LocalDate date, Double value) {
        this.code = code;
        this.date = date;
        this.value = value;
        this.invoice = invoice;
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

    public Bill setInvoice(Invoice invoice) {
        this.invoice = invoice;
        return this;
    }

}
