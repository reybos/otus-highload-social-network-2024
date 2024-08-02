package rey.bos.highload.sn.external.shared.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.external.model.CreatePostRequest;
import rey.bos.highload.sn.external.model.Post;

@Mapper(componentModel="spring")
public abstract class PostDtoMapper {

    public PostDto map(CreatePostRequest request) {
        return PostDto.builder().content(request.getText()).build();
    }

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "text", source = "content")
    public abstract Post map(PostDto post);

}
