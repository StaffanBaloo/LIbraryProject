package exceptions;

public class CantSaveAuthorException extends RuntimeException {
    public CantSaveAuthorException(String message) {
        super(message);
    }
}
