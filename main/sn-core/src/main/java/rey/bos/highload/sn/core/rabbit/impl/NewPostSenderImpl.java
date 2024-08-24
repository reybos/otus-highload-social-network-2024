package rey.bos.highload.sn.core.rabbit.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rey.bos.highload.sn.core.io.repository.FriendRepository;
import rey.bos.highload.sn.core.rabbit.NewPostSender;
import rey.bos.highload.sn.core.shared.dto.PostDto;
import rey.bos.highload.sn.core.websocket.NewPostHandler;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NewPostSenderImpl implements NewPostSender {

    private final FriendRepository friendRepository;
    private final NewPostHandler newPostHandler;

    @Override
    public void sendNewPost(PostDto postDto) {
        List<String> subscriberIds = friendRepository.findSubscriber(postDto.getAuthorUserId());
        subscriberIds.forEach(userId -> newPostHandler.sendToUser(userId, postDto));
    }

}
