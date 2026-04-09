package exceptions;

public class LoanRenewException extends RuntimeException {
    public LoanRenewException(String message) {
        super(message);
    }
}
