package bill_processor;

import bill_processor.controller.payment.PaymentController;
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
    private final PaymentController paymentController;

    public BillProcessor() {
        this.paymentController = new PaymentController();
        this.invoices = new HashMap<>();
    }

    public Payment payBill(Bill bill, PaymentTypeEnum type) {
        validatePayment(bill, type);

        Double paymentValue = bill.getValue();

        boolean isTypeBoletoAndPaymentDateIsAfterBillDate = type.equals(PaymentTypeEnum.BOLETO) && LocalDate.now().isAfter(bill.getDate());

        if(isTypeBoletoAndPaymentDateIsAfterBillDate) {
            paymentValue = bill.getValue() + (bill.getValue() * 0.10);
        }

        Payment payment = paymentController.create(paymentValue, LocalDate.now(), type);
        bill.setPayment(payment);

        return payment;
    }

    private void validatePayment(Bill bill, PaymentTypeEnum type) {
        LocalDate invoiceDate = bill.getInvoice().getDate();
        LocalDate invoiceDateMinus15Days = invoiceDate.minusDays(15);
        LocalDate billDate = bill.getDate();

        boolean isBillDateAfterInvoiceDate = billDate.isAfter(invoiceDate);
        boolean isPaymentTypeCartaoCreditoAndDateIsValid = type.equals(PaymentTypeEnum.CARTAO_CREDITO)
                && invoiceDateMinus15Days.isBefore(billDate);

        if(isBillDateAfterInvoiceDate) {
            throw new IllegalArgumentException("Not possible to payBill when bill date is after invoice date");
        } else if (isPaymentTypeCartaoCreditoAndDateIsValid) {
            throw new IllegalArgumentException("Not possible to payBill when payment type is cartao de credito and bill date is not at least 15 days before invoice date");
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
