package rey.bos.highload.sn.core.io.repository.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostFeed {

    private String postId;

    private String content;

    private String authorUserId;

}
