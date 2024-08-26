package rey.bos.highload.sn.core.io.repository.custom;

import rey.bos.highload.sn.core.io.entity.Friend;

public interface FriendRepositoryCustom {

    Friend findFriendOrThrow(long userId, long friendUserId);

}
