package bill_processor.controller.bill;

import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;

public class BillController {

    public Bill create(Invoice invoice, String code, LocalDate date, Double value) {
        return new Bill(invoice, code, date, value);
    }

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

        if(type.equals(PaymentTypeEnum.BOLETO)) {
            Double value = bill.getValue() + (bill.getValue() * 0.10);
            payment.setValue(value);
        } else {
            payment.setValue(bill.getValue());
        }

        bill.setPayment(payment);
        return payment;
    }
}
