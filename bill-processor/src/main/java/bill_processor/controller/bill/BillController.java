package bill_processor.controller.bill;

import bill_processor.model.bill.Bill;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class BillController {

    public Bill create(String code, LocalDate date, Double value) {
        return new Bill(code, date, value);
    }

    public Payment pay(Bill bill, PaymentTypeEnum type) {
        Payment payment = new Payment()
                .setDate(LocalDate.now())
                .setType(type);

        if(type.equals(PaymentTypeEnum.BOLETO)) {
            Double value = bill.getValue() + (bill.getValue() * 0.10);
            payment.setValue(value);
        } else {
            payment.setValue(bill.getValue());
        }

        return payment;
    }
}
