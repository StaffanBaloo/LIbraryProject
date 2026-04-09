package exceptions;

public class LoanCreationException extends RuntimeException {
    public LoanCreationException(String message) {
        super(message);
    }
}
