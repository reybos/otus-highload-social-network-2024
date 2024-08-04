package rey.bos.highload.sn.core.service;

import rey.bos.highload.sn.core.shared.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost(String userId, PostDto postDto);

    void deletePost(String userId, String postId);

    PostDto getPost(String userId, String postId);

    void updatePost(String userId, String postId, PostDto postDto);

    List<PostDto> getPostFeed(String userId, Integer offset, Integer limit);

}
