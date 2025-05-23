package rey.bos.highload.sn.core.cache.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;
import rey.bos.highload.sn.core.cache.PostCacheService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostCacheServiceImpl implements PostCacheService {

    private static final String CACHE_PREFIX = "user_posts:";

    private final RedisTemplate<String, PostFeed> redisTemplate;

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
    /*
      The method will return posts if
      - there are more entries in the cache than requested
      - there are fewer entries in the cache than requested, but since the cache is not fully filled, there will also be fewer in the database
      If an empty list is returned, then you need to go to the database
     */
    public List<PostFeed> getPosts(String userId, int offset, int limit) {
        String key = CACHE_PREFIX + userId;
        int cacheSize = getCacheSize(userId);
        if (offset >= cacheSize || cacheSize == 0) {
            return new ArrayList<>();
        }
        if (offset + limit > cacheSize && cacheSize < getPostCashSizeDefault()) {
            limit = cacheSize;
        }
        List<PostFeed> postList = redisTemplate.opsForList().range(key, offset, offset + limit - 1);
        return postList == null ? new ArrayList<>() : postList;
    }

    @Override
    public void updateCache(String userId, List<PostFeed> posts) {
        String key = CACHE_PREFIX + userId;
        redisTemplate.delete(key);
        redisTemplate.opsForList().rightPushAll(key, posts);
    }

}
