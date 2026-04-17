package exceptions;

public class CantSaveCategoryException extends RuntimeException {
    public CantSaveCategoryException(String message) {
        super(message);
    }
}
