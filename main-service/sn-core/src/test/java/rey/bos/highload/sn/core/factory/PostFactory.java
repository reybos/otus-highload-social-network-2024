package rey.bos.highload.sn.core.factory;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rey.bos.highload.sn.core.service.PostService;
import rey.bos.highload.sn.core.shared.dto.PostDto;

@Component
@Profile("test")
@RequiredArgsConstructor
public class PostFactory {

    private final UserFactory userFactory;
    private final PostService postService;

    public PostDto createPost(PostParams params) {
        String userId = params.userId;
        if (userId == null) {
            userId = userFactory.createUser().getUserId();
        }
        return postService.createPost(
            userId, PostDto.builder().content(params.getContent()).build()
        );
    }

    public PostDto createPost() {
        return createPost(PostParams.builder().build());
    }

    @Builder
    @Getter
    public static class PostParams {

        private String userId;

        @Builder.Default
        private String content = RandomStringUtils.randomAlphanumeric(20);

    }

}
