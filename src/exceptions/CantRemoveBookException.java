package exceptions;

public class CantRemoveBookException extends RuntimeException {
    public CantRemoveBookException(String message) {
        super(message);
    }
}
