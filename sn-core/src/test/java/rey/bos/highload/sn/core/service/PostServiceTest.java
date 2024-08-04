package rey.bos.highload.sn.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rey.bos.highload.sn.core.TestClass;
import rey.bos.highload.sn.core.exception.PostNotFoundException;
import rey.bos.highload.sn.core.factory.FriendFactory;
import rey.bos.highload.sn.core.factory.PostFactory;
import rey.bos.highload.sn.core.factory.UserFactory;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.core.shared.dto.UserDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostServiceTest extends TestClass {

    @Autowired
    private PostService postService;

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private PostFactory postFactory;

    @Autowired
    private FriendFactory friendFactory;

    @Test
    public void whenCreatePostThenSuccess() {
        UserDto userDto = userFactory.createUser();
        PostDto postDto = PostDto.builder().content("Test").build();
        PostDto storedPost = postService.createPost(userDto.getUserId(), postDto);
        assertThat(storedPost.getContent()).isEqualTo(postDto.getContent());
        assertThat(storedPost.getPostId()).isNotNull();
    }

    @Test
    public void whenDeletePostThenSuccess() {
        PostDto postDto = postFactory.createPost();
        postService.deletePost(postDto.getAuthorUserId(), postDto.getPostId());
        assertThrows(
            PostNotFoundException.class,
            () -> postService.getPost(postDto.getAuthorUserId(), postDto.getPostId())
        );
    }

    @Test
    public void whenGetPostThenSuccess() {
        PostDto postDto = postFactory.createPost();
        PostDto storedPost = postService.getPost(postDto.getAuthorUserId(), postDto.getPostId());
        assertThat(storedPost).isEqualTo(postDto);
    }

    @Test
    public void whenUpdatePostThenSuccess() {
        PostDto postDto = postFactory.createPost();
        String newContent = postDto.getContent() + "-1";
        postService.updatePost(
            postDto.getAuthorUserId(), postDto.getPostId(), PostDto.builder().content(newContent).build()
        );
        PostDto storedPost = postService.getPost(postDto.getAuthorUserId(), postDto.getPostId());
        assertThat(storedPost.getContent()).isEqualTo(newContent);
    }

    @Test
    public void whenGetFeedFromDbThenSuccess() {
        UserDto userDto = userFactory.createUser();
        FriendDto friendDto = friendFactory.addFriend(userDto.getUserId());
        PostDto postDto = postFactory.createPost(
            PostFactory.PostParams.builder().userId(friendDto.getFriendUserId()).build()
        );
        List<PostDto> posts = postService.getPostFeed(userDto.getUserId(), null, null);
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0)).isEqualTo(postDto);
    }

}