package exceptions;

public class CantCreateNoteException extends RuntimeException {
    public CantCreateNoteException(String message) {
        super(message);
    }
}
