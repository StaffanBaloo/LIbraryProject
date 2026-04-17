package exceptions;

public class CantDeleteAuthorException extends RuntimeException {
    public CantDeleteAuthorException(String message) {
        super(message);
    }
}
