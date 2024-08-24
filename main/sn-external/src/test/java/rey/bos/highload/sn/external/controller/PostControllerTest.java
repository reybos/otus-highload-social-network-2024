package rey.bos.highload.sn.external.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rey.bos.highload.sn.core.TestUtil;
import rey.bos.highload.sn.core.exception.PostNotFoundException;
import rey.bos.highload.sn.core.factory.FriendFactory;
import rey.bos.highload.sn.core.factory.PostFactory;
import rey.bos.highload.sn.core.factory.UserFactory;
import rey.bos.highload.sn.core.service.PostService;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.core.shared.dto.UserDto;
import rey.bos.highload.sn.core.util.JwtUtil;
import rey.bos.highload.sn.external.TestClass;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest extends TestClass {

    @Autowired
    private UserFactory userFactory;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostFactory postFactory;

    @Autowired
    private PostService postService;

    @Autowired
    private FriendFactory friendFactory;

    @Test
    public void whenCreatePostThenSuccess() throws Exception {
        UserDto user = userFactory.createUser();
        String request = testUtil.readJson("request/post/create_post.json", "Test");
        String token = jwtUtil.generateJwtToken(user.getUserId());

        mockMvc.perform(post(String.format("/user/%s/post/create", user.getUserId()))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void whenGetPostThenSuccess() throws Exception {
        PostDto postDto = postFactory.createPost();
        String token = jwtUtil.generateJwtToken(postDto.getAuthorUserId());
        String expected = testUtil.readJson(
            "response/post/get_post.json", postDto.getPostId(), postDto.getContent(), postDto.getAuthorUserId()
        );
        mockMvc.perform(get(String.format("/user/%s/post/%s", postDto.getAuthorUserId(), postDto.getPostId()))
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().json(expected));
    }

    @Test
    public void whenDeletePostThenSuccess() throws Exception {
        PostDto postDto = postFactory.createPost();
        String token = jwtUtil.generateJwtToken(postDto.getAuthorUserId());
        mockMvc.perform(put(String.format("/user/%s/post/%s/delete", postDto.getAuthorUserId(), postDto.getPostId()))
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk());
        assertThrows(
            PostNotFoundException.class,
            () -> postService.getPost(postDto.getAuthorUserId(), postDto.getPostId())
        );
    }

    @Test
    public void whenUpdatePostThenSuccess() throws Exception {
        PostDto postDto = postFactory.createPost();
        String token = jwtUtil.generateJwtToken(postDto.getAuthorUserId());
        String newContent = postDto.getContent() + "-1";
        String request = testUtil.readJson("request/post/update_post.json", newContent);
        mockMvc.perform(put(String.format("/user/%s/post/%s/update", postDto.getAuthorUserId(), postDto.getPostId()))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
            .andExpect(status().isOk());
        PostDto storedPost = postService.getPost(postDto.getAuthorUserId(), postDto.getPostId());
        assertThat(storedPost.getContent()).isEqualTo(newContent);
    }

    @Test
    public void whenGetFeedPostThenSuccess() throws Exception {
        UserDto userDto = userFactory.createUser();
        FriendDto friendDto = friendFactory.addFriend(userDto.getUserId());
        PostDto postDto = postFactory.createPost(
            PostFactory.PostParams.builder().userId(friendDto.getFriendUserId()).build()
        );
        String token = jwtUtil.generateJwtToken(userDto.getUserId());
        String expected = testUtil.readJson(
            "response/post/get_feed.json", postDto.getPostId(), postDto.getContent(), postDto.getAuthorUserId()
        );
        mockMvc.perform(get(String.format("/user/%s/post/feed", userDto.getUserId()))
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(content().json(expected));
    }

}