package rey.bos.highload.sn.core.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import rey.bos.highload.sn.core.io.repository.model.PostFeed;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
            redisProperties.getRedisHost(),
            redisProperties.getRedisPort()
        );
    }

    @Bean
    public RedisTemplate<String, PostFeed> redisTemplate(LettuceConnectionFactory connectionFactory) {
        final var redisTemplate = new RedisTemplate<String, PostFeed>();
        redisTemplate.setConnectionFactory(connectionFactory);

        final var objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL,  JsonAutoDetect.Visibility.ANY);
        final var jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, PostFeed.class);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
