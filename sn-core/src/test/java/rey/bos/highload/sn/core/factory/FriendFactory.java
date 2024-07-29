package rey.bos.highload.sn.core.factory;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import rey.bos.highload.sn.core.service.FriendService;
import rey.bos.highload.sn.core.shared.dto.FriendDto;

@Component
@Profile("test")
@RequiredArgsConstructor
public class FriendFactory {

    private final FriendService friendService;
    private final UserFactory userFactory;

    public FriendDto addFriend(String userId, FriendParams friendParams) {
        String friendUserId = friendParams.getFriendUserId() == null
            ? userFactory.createUser().getUserId()
            : friendParams.getFriendUserId();
        FriendDto friendDto = FriendDto.builder().friendUserId(friendUserId).build();
        friendService.setFriend(userId, friendDto);
        return friendDto;
    }

    public FriendDto addFriend(String userId) {
        return addFriend(userId, FriendParams.builder().build());
    }

    @Builder
    @Getter
    public static class FriendParams {

        private String friendUserId;

    }

}
