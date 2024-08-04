package rey.bos.highload.sn.core.shared.mapper;

import org.mapstruct.Mapper;
import rey.bos.highload.sn.core.io.entity.Post;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;
import rey.bos.highload.sn.core.shared.dto.PostDto;

import java.util.List;

@Mapper(componentModel="spring")
public abstract class PostMapper {

    public abstract PostDto map(Post post, String authorUserId);

    public abstract PostDto map(PostFeed postFeed);

    public abstract List<PostDto> map(List<PostFeed> postFeed);

    public Post map(PostDto postDto) {
        return Post.builder()
            .content(postDto.getContent())
            .build();
    }

}
