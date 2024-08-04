package rey.bos.highload.sn.external.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.sn.core.service.PostService;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.external.api.PostApi;
import rey.bos.highload.sn.external.model.CreatePostRequest;
import rey.bos.highload.sn.external.model.Post;
import rey.bos.highload.sn.external.shared.mapper.PostDtoMapper;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController implements PostApi {

    private final PostDtoMapper postDtoMapper;
    private final PostService postService;

    @Override
    public ResponseEntity<String> createPost(String userId, CreatePostRequest createPostRequest) {
        PostDto postDto = postDtoMapper.map(createPostRequest);
        PostDto storedPost = postService.createPost(userId, postDto);
        return ResponseEntity.ok(storedPost.getPostId());
    }

    @Override
    public ResponseEntity<Void> deletePost(String userId, String id) {
        postService.deletePost(userId, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updatePost(String userId, String id, CreatePostRequest createPostRequest) {
        PostDto postDto = postDtoMapper.map(createPostRequest);
        postService.updatePost(userId, id, postDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Post> getPost(String userId, String id) {
        PostDto postDto = postService.getPost(userId, id);
        return ResponseEntity.ok(postDtoMapper.map(postDto));
    }

    @Override
    public ResponseEntity<List<Post>> getPostFeed(String userId, Integer offset, Integer limit) {
        List<PostDto> postDtos = postService.getPostFeed(userId, offset, limit);
        return  ResponseEntity.ok(postDtoMapper.map(postDtos));
    }

}
