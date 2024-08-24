package rey.bos.highload.sn.external.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import rey.bos.highload.sn.core.service.FriendService;
import rey.bos.highload.sn.core.shared.dto.FriendDto;
import rey.bos.highload.sn.external.api.FriendApi;
import rey.bos.highload.sn.external.model.FriendRequest;
import rey.bos.highload.sn.external.shared.mapper.FriendDtoMapper;

@RequiredArgsConstructor
@Controller
public class FriendController implements FriendApi {

    private final FriendDtoMapper friendDtoMapper;
    private final FriendService friendService;

    @Override
    public ResponseEntity<Void> deleteFriend(String userId, FriendRequest friendRequest) {
        FriendDto friendDto = friendDtoMapper.map(friendRequest);
        friendService.deleteFriend(userId, friendDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> setFriend(String userId, FriendRequest friendRequest) {
        FriendDto friendDto = friendDtoMapper.map(friendRequest);
        friendService.setFriend(userId, friendDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
