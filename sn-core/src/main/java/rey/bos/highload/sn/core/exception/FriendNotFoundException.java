package rey.bos.highload.sn.core.exception;

public class FriendNotFoundException extends RuntimeException {

    public FriendNotFoundException(long userId, long friendId) {
        super(String.format("The friend with userId %d and friendId %d was not found", userId, friendId));
    }

    public FriendNotFoundException(String userId, String friendId) {
        super(String.format("The friend with userId %s and friendId %s was not found", userId, friendId));
    }

}
