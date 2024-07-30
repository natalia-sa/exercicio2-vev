package bill_processor.model.payment;

import bill_processor.model.payment.enums.PaymentTypeEnum;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Payment {

    private Double value;
    private LocalDate date;
    private PaymentTypeEnum type;

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

}
