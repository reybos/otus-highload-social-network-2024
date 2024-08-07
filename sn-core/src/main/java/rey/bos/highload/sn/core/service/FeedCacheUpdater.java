package rey.bos.highload.sn.core.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public interface FeedCacheUpdater {

    @RabbitListener(queues = "changePostQueue")
    void handleChangePost(String authorUserId);

    @RabbitListener(queues = "changeFriendQueue")
    void handleChangeFriend(String userId);

}
