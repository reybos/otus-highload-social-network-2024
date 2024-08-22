package rey.bos.highload.sn.core.cache;

import rey.bos.highload.sn.core.io.repository.model.PostFeed;

import java.util.List;

public interface PostCacheService {

    int getCacheSize(String userId);

    int getPostCashSizeDefault();

    List<PostFeed> getPosts(String userId, int offset, int limit);

    void updateCache(String userId, List<PostFeed> posts);

}
