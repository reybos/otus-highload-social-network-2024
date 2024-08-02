package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import rey.bos.highload.sn.core.exception.PostNotFoundException;
import rey.bos.highload.sn.core.io.entity.Post;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.io.repository.PostRepository;
import rey.bos.highload.sn.core.io.repository.UserRepository;
import rey.bos.highload.sn.core.service.PostService;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.core.shared.mapper.PostMapper;
import rey.bos.highload.sn.core.util.Utils;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Utils utils;
    private final Clock clock;

    @Value("${post.id.length}")
    private int postIdLength;

    @Override
    @Retryable(retryFor = DataIntegrityViolationException.class)
    public PostDto createPost(String userId, PostDto postDto) {
        User user = userRepository.findByUserIdOrThrow(userId);
        Post post = postMapper.map(postDto);
        post.setUserId(user.getId());
        post.setPostId(utils.generatePostId(postIdLength));
        post.setDeleted(false);
        post.setCreatedAt(Instant.now(clock));
        post.setUpdatedAt(Instant.now(clock));
        Post storedPost = postRepository.save(post);
        return postMapper.map(storedPost, userId);
    }

    @Override
    public void deletePost(String userId, String postId) {
        Optional<Post> postO = postRepository.findUserPostById(postId, userId);
        if (postO.isEmpty()) {
            return;
        }
        Post post = postO.get();
        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    public PostDto getPost(String userId, String postId) {
        Post post = getPostOrThrow(userId, postId);
        return postMapper.map(post, userId);
    }

    @Override
    public void updatePost(String userId, String postId, PostDto postDto) {
        Post post = getPostOrThrow(userId, postId);
        post.setContent(postDto.getContent());
        postRepository.save(post);
    }

    private Post getPostOrThrow(String userId, String postId) {
        Optional<Post> postO = postRepository.findUserPostById(postId, userId);
        return postO.orElseThrow(() -> new PostNotFoundException(userId, postId));
    }

}
