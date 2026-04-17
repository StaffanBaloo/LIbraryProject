package exceptions;

public class CantCreateCategoryException extends RuntimeException {
    public CantCreateCategoryException(String message) {
        super(message);
    }
}
