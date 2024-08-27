package rey.bos.highload.sn.core.io.repository.custom.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import rey.bos.highload.sn.core.exception.FriendNotFoundException;
import rey.bos.highload.sn.core.io.entity.Friend;
import rey.bos.highload.sn.core.io.repository.FriendRepository;
import rey.bos.highload.sn.core.io.repository.custom.FriendRepositoryCustom;

import java.util.Optional;

@RequiredArgsConstructor
public class FriendRepositoryCustomImpl implements FriendRepositoryCustom {

    private final ApplicationContext context;
    private FriendRepository friendRepository;

    private FriendRepository getFriendRepository() {
        if (friendRepository == null) {
            friendRepository = context.getBean(FriendRepository.class);
        }
        return friendRepository;
    }

    @Override
    public Friend findFriendOrThrow(long userId, long friendId) {
        Optional<Friend> friendO = getFriendRepository().findFriend(userId, friendId);
        return friendO.orElseThrow(
            () -> new FriendNotFoundException(userId, friendId)
        );
    }

}
