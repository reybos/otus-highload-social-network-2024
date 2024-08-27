package rey.bos.highload.dialog.exception;

public class ParticipantNotFoundException extends RuntimeException {

    public ParticipantNotFoundException(long dialogId, String userId) {
        super(String.format("The participant with dialogId %s and userId %s was not found", dialogId, userId));
    }

}
