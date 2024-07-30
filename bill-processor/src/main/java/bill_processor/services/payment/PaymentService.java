package bill_processor.services.payment;

import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class PaymentService {

    public Payment create(Double value, LocalDate date, PaymentTypeEnum type) {
        if(type.equals(PaymentTypeEnum.BOLETO) && (value < 0.01 || value > 5000)) {
            throw new IllegalArgumentException("Invalid value");
        }

        return new Payment(value, date, type);
    }
}
