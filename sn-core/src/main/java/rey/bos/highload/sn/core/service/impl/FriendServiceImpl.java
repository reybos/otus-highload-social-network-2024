package rey.bos.highload.sn.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rey.bos.highload.sn.core.exception.FriendNotFoundException;
import rey.bos.highload.sn.core.io.entity.Friend;
import rey.bos.highload.sn.core.io.entity.User;
import rey.bos.highload.sn.core.io.repository.FriendRepository;
import rey.bos.highload.sn.core.io.repository.UserRepository;
import rey.bos.highload.sn.core.service.FriendService;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.core.shared.mapper.FriendMapper;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;

    @Override
    public void setFriend(String userId, FriendDto friendDto) {
        User currUser = userRepository.findByUserIdOrThrow(userId);
        User friendUser = userRepository.findByUserIdOrThrow(friendDto.getFriendUserId());
        Friend friend = Friend.builder().userId(currUser.getId()).friendId(friendUser.getId()).deleted(false).build();
        friendRepository.save(friend);
    }

    @Override
    public void deleteFriend(String userId, FriendDto friendDto) {
        User currUser = userRepository.findByUserIdOrThrow(userId);
        User friendUser = userRepository.findByUserIdOrThrow(friendDto.getFriendUserId());
        Friend friend = friendRepository.findFriendOrThrow(currUser.getId(), friendUser.getId());
        friend.setDeleted(true);
        friendRepository.save(friend);
    }

    @Override
    public FriendDto findByIdsOrThrow(String userId, String friendUserId) {
        if (friendRepository.findFriend(userId, friendUserId).isEmpty()) {
            throw new FriendNotFoundException(userId, friendUserId);
        }
        User friend = userRepository.findByUserIdOrThrow(friendUserId);
        return friendMapper.map(friend);
    }

}
