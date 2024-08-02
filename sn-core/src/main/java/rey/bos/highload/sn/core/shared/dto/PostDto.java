package rey.bos.highload.sn.core.shared.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDto {

    private String postId;

    private String content;

    private String authorUserId;

}
