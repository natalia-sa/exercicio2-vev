package bill_processor.exceptions;

public class InvalidPaymentDataException extends  IllegalArgumentException {

    public InvalidPaymentDataException() {
        super("Payment data is invalid");
    }
}
