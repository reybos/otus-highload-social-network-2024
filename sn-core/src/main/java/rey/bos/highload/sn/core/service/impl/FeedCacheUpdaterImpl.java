package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rey.bos.highload.sn.core.io.repository.FriendRepository;
import rey.bos.highload.sn.core.io.repository.PostRepository;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;
import rey.bos.highload.sn.core.service.FeedCacheUpdater;
import rey.bos.highload.sn.core.service.PostCacheService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedCacheUpdaterImpl implements FeedCacheUpdater {

    private final FriendRepository friendRepository;
    private final PostCacheService postCacheService;
    private final PostRepository postRepository;

    @Override
    public void handleChangePost(String authorUserId) {
        List<String> subscriberIds = friendRepository.findSubscriber(authorUserId);
        subscriberIds.forEach(this::updateUserCache);
    }

    @Override
    public void handleChangeFriend(String userId) {
        updateUserCache(userId);
    }

    private void updateUserCache(String userId) {
        List<PostFeed> feeds = postRepository.findLatestPostsByUserId(
            userId, 0, postCacheService.getPostCashSizeDefault()
        );
        postCacheService.updateCache(userId, feeds);
    }

}
