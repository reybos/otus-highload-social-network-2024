package rey.bos.highload.sn.core.io.repository.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostFeed {

    private String postId;

    private String content;

    private String authorUserId;

}
