package bill_processor.service.payment;

import bill_processor.exceptions.InvalidPaymentDataException;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class PaymentService {

    private static final Double BOLETO_MINIMUM_VALUE = 0.01;
    private static final Double BOLETO_MAXIMUM_VALUE = 5000.0;

    public Payment create(Double value, LocalDate date, PaymentTypeEnum type) {
        if(type.equals(PaymentTypeEnum.BOLETO) && (value < BOLETO_MINIMUM_VALUE || value > BOLETO_MAXIMUM_VALUE)) {
            throw new InvalidPaymentDataException();
        }

        return new Payment(value, date, type);
    }
}
