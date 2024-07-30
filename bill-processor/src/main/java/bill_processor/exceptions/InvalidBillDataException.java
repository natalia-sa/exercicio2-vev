package bill_processor.exceptions;

public class InvalidBillDataException extends  IllegalArgumentException {

    public InvalidBillDataException() {
        super("Bill data is invalid");
    }
}
