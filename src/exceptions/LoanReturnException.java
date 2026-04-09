package exceptions;

public class LoanReturnException extends RuntimeException {
    public LoanReturnException(String message) {
        super(message);
    }
}
