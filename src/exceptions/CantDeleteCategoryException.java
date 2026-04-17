package exceptions;

public class CantDeleteCategoryException extends RuntimeException {
    public CantDeleteCategoryException(String message) {
        super(message);
    }
}
