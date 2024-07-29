package bill_processor.model.invoice;

import bill_processor.model.invoice.enums.InvoiceStatusEnum;

import java.time.LocalDate;
import java.util.Objects;

public class Invoice {

    private LocalDate date;
    private Double totalValue;
    private String customerName;
    private InvoiceStatusEnum status;

    public Invoice() {
    }

    public Invoice(LocalDate date, Double totalValue, String customerName) {
        this.date = date;
        this.totalValue = totalValue;
        this.customerName = customerName;
        this.status = InvoiceStatusEnum.PENDENTE;
    }

    public Invoice setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public Invoice setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
        return this;
    }

    public Invoice setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Invoice setStatus(InvoiceStatusEnum status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this==o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(date, invoice.date) && Objects.equals(totalValue, invoice.totalValue) && Objects.equals(customerName, invoice.customerName) && status==invoice.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, totalValue, customerName, status);
    }
}
