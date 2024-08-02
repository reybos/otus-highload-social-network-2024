package rey.bos.highload.sn.core.io.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("post")
@Data
@Builder
public class Post {

    @Id
    private Long id;

    private String postId;

    private long userId;

    private boolean deleted;

    private String content;

    private Instant updatedAt;

    private Instant createdAt;

}
