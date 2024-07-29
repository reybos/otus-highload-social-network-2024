package rey.bos.highload.sn.core.service;

import rey.bos.highload.sn.core.shared.dto.FriendDto;

public interface FriendService {

    void setFriend(String userId, FriendDto friendDto);

    void deleteFriend(String userId, FriendDto friendDto);

    FriendDto findByIdsOrThrow(String userId, String friendUserId);

}
