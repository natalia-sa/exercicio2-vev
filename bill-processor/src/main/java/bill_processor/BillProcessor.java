package bill_processor;

import bill_processor.controller.bill.BillController;
import bill_processor.controller.payment.PaymentController;
import bill_processor.model.bill.Bill;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class BillProcessor {

    private BillController billController;
    private PaymentController paymentController;

    public Payment pay(Bill bill, PaymentTypeEnum type) {
        if(bill.getDate().isAfter(bill.getInvoice().getDate())) {
            throw new IllegalArgumentException("Not possible to pay");
        }

        if(type.equals(PaymentTypeEnum.CARTAO_CREDITO)
                && bill.getInvoice().getDate().minusDays(15).isBefore(bill.getDate())) {
            throw new IllegalArgumentException("Not possible to pay");
        }

        Payment payment = new Payment()
                .setDate(LocalDate.now())
                .setType(type);

        if(type.equals(PaymentTypeEnum.BOLETO) && LocalDate.now().isAfter(bill.getDate())) {
            Double value = bill.getValue() + (bill.getValue() * 0.10);
            payment.setValue(value);
        } else {
            payment.setValue(bill.getValue());
        }

        bill.setPayment(payment);
        return payment;
    }
}
