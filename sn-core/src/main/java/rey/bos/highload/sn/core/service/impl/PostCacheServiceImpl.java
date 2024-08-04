package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;
import rey.bos.highload.sn.core.service.PostCacheService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostCacheServiceImpl implements PostCacheService {

    private static final String CACHE_PREFIX = "user_posts:";

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${post.cash.size}")
    private int postCashSizeDefault;

    @Override
    public int getCacheSize(String userId) {
        String key = CACHE_PREFIX + userId;
        Long size = redisTemplate.opsForList().size(key);
        return size == null ? 0 : size.intValue();
    }

    @Override
    public int getPostCashSizeDefault() {
        return postCashSizeDefault;
    }

    @Override
    public List<PostFeed> getPosts(String userId, int offset, int limit) {
        String key = CACHE_PREFIX + userId;
        int cacheSize = getCacheSize(userId);
        if (offset + limit > cacheSize) {
            return new ArrayList<>();
        }
        List<Object> postList = redisTemplate.opsForList().range(key, offset, offset + limit - 1);
        if (postList == null) {
            return new ArrayList<>();
        }
        return postList.stream().map(post -> (PostFeed) post).collect(Collectors.toList());
    }

    @Override
    public void updateCache(String userId, List<PostFeed> posts) {
        String key = CACHE_PREFIX + userId;
        redisTemplate.delete(key);
        redisTemplate.opsForList().rightPushAll(key, posts);
    }

}
