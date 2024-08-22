package rey.bos.highload.sn.core.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import rey.bos.highload.sn.core.shared.dto.PostDto;

public interface NewPostSender {

    @RabbitListener(queues = "newPostQueue")
    void sendNewPost(PostDto postDto);

}
