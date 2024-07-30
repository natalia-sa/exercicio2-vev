package bill_processor.controller.payment;

import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class PaymentController {

    public Payment create(Double value, LocalDate date, PaymentTypeEnum type) {
        return new Payment(value, date, type);
    }
}
