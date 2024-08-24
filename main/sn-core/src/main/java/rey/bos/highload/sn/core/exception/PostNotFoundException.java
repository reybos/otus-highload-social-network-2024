package rey.bos.highload.sn.core.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(String userId, String postId) {
        super(String.format("The post with userId %s and postId %s was not found", userId, postId));
    }

}
