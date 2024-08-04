package rey.bos.highload.sn.core.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class RedisProperties {

    private final int redisPort;
    private final String redisHost;

    public RedisProperties(
        @Value("${spring.data.redis.port}") int redisPort,
        @Value("${spring.data.redis.host}") String redisHost) {
        this.redisPort = redisPort;
        this.redisHost = redisHost;
    }

}