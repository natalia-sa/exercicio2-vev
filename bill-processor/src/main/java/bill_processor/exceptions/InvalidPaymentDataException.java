package bill_processor.exceptions;

public class InvalidPaymentDataException extends  IllegalArgumentException {

    public InvalidPaymentDataException() {
        super("Payment value is invalid");
    }
}
