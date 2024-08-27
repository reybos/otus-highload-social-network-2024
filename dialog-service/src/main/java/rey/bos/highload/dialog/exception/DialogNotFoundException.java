package rey.bos.highload.dialog.exception;

public class DialogNotFoundException extends RuntimeException {

    public DialogNotFoundException(String dialogId) {
        super(String.format("The dialog with dialogId %s was not found", dialogId));
    }

}
