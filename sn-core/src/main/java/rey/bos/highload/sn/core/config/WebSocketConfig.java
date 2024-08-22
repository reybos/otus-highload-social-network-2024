package rey.bos.highload.sn.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import rey.bos.highload.sn.core.websocket.NewPostHandler;

import static org.springframework.messaging.simp.SimpMessageType.*;

@RequiredArgsConstructor
@EnableWebSocket
@EnableWebSocketSecurity
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final NewPostHandler newPostHandler;

    @Bean
    public AuthorizationManager<Message<?>> messageAuthorizationManager(
        MessageMatcherDelegatingAuthorizationManager.Builder messages
    ) {
        messages
            .anyMessage().authenticated()
            .nullDestMatcher().permitAll()
            .simpSubscribeDestMatchers("**").authenticated()
            .simpDestMatchers("**").authenticated()
            .simpSubscribeDestMatchers("**").authenticated()
            .simpTypeMatchers(CONNECT, CONNECT_ACK, MESSAGE, SUBSCRIBE).authenticated()
            .anyMessage().authenticated();

        return messages.build();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
            .addHandler(newPostHandler, "/post/feed/posted")
            .setAllowedOrigins("*");
    }

}
