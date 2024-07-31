package bill_processor;

import bill_processor.exceptions.InvalidBillDataException;
import bill_processor.service.payment.PaymentService;
import bill_processor.model.bill.Bill;
import bill_processor.model.invoice.Invoice;
import bill_processor.model.invoice.enums.InvoiceStatusEnum;
import bill_processor.model.payment.Payment;
import bill_processor.model.payment.enums.PaymentTypeEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BillProcessor {

    private final Map<Invoice, List<Bill>> invoices;
    private final PaymentService paymentService;
    private static final Double BOLETO_FEE = 0.10;
    private static final int MINIMUM_DAYS_IN_ADVANCE_TO_PAY_WITH_CARTAO_CREDITO = 15;

    public BillProcessor() {
        this.paymentService = new PaymentService();
        this.invoices = new HashMap<>();
    }

    public Payment payBill(Bill bill, PaymentTypeEnum paymentType) {
        validatePayment(bill, paymentType);

        Double paymentValue = bill.getValue();

        boolean isTypeBoletoAndPaymentDateIsAfterBillDate = paymentType.equals(PaymentTypeEnum.BOLETO)
                && LocalDate.now().isAfter(bill.getDate());

        if(isTypeBoletoAndPaymentDateIsAfterBillDate) {
            paymentValue = bill.getValue() + (bill.getValue() * BOLETO_FEE);
        }

        Payment payment = paymentService.create(paymentValue, LocalDate.now(), paymentType);
        bill.setPayment(payment);

        return payment;
    }

    private void validatePayment(Bill bill, PaymentTypeEnum type) {
        LocalDate invoiceDate = bill.getInvoice().getDate();
        LocalDate invoiceDateMinus15Days = invoiceDate.minusDays(MINIMUM_DAYS_IN_ADVANCE_TO_PAY_WITH_CARTAO_CREDITO);
        LocalDate billDate = bill.getDate();

        boolean isBillDateAfterInvoiceDate = billDate.isAfter(invoiceDate);
        boolean isPaymentTypeCartaoCreditoAndDateIsValid = type.equals(PaymentTypeEnum.CARTAO_CREDITO)
                && invoiceDateMinus15Days.isBefore(billDate);

        if(isBillDateAfterInvoiceDate || isPaymentTypeCartaoCreditoAndDateIsValid) {
            throw new InvalidBillDataException();
        }
    }

    public void processBills(List<Bill> bills) {
        bills.forEach(bill -> {
            Invoice invoice = bill.getInvoice();
            this.invoices.putIfAbsent(invoice, new ArrayList<>());
            this.invoices.get(invoice).add(bill);
            updateInvoiceStatusIfTotalValueWasPaid(invoice);
        });
    }

    private void updateInvoiceStatusIfTotalValueWasPaid(Invoice invoice) {
        Double invoiceValue = invoice.getTotalValue();
        List<Bill> bills = this.invoices.get(invoice);
        Double paidValue = 0.0;

        for(Bill bill : bills) {
            if(bill.getPayment() != null) {
                paidValue += bill.getPayment().getValue();
            }
        }

        if(paidValue >= invoiceValue) {
            invoice.setStatus(InvoiceStatusEnum.PAGA);
        }
    }
}
