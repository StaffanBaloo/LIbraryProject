package exceptions;

public class CantCreateAuthorException extends RuntimeException {
    public CantCreateAuthorException(String message) {
        super(message);
    }
}
