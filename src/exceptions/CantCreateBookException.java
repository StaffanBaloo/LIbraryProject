package exceptions;

public class CantCreateBookException extends RuntimeException {
    public CantCreateBookException(String message) {
        super(message);
    }
}
