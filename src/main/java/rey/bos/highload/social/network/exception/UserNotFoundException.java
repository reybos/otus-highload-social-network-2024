package rey.bos.highload.social.network.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super(String.format("The user with id %s was not found", userId));
    }

}