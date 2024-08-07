package rey.bos.highload.sn.core.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue changePostQueue() {
        return new Queue("changePostQueue", true);
    }

    @Bean
    public Queue changeFriendQueue() {
        return new Queue("changeFriendQueue", true);
    }

}