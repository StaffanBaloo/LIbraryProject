package exceptions;

public class CantCreateLoanException extends RuntimeException {
    public CantCreateLoanException(String message) {
        super(message);
    }
}
