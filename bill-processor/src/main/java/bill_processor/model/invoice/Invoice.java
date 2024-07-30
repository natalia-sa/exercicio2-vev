package bill_processor.model.invoice;

import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Invoice {

    private LocalDate date;
    private Double totalValue;
    private String customerName;
    private InvoiceStatusEnum status;

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
}
