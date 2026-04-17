package exceptions;

public class CantCreateMemberException extends RuntimeException {
    public CantCreateMemberException(String message) {
        super(message);
    }
}
