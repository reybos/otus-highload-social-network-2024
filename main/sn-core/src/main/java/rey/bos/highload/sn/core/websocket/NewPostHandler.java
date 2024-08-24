package rey.bos.highload.sn.core.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import rey.bos.highload.sn.core.shared.dto.PostDto;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Component
@Slf4j
public class NewPostHandler extends TextWebSocketHandler {

    Map<String, WebSocketSession> webSocketSessions = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal principal = session.getPrincipal();
        super.afterConnectionEstablished(session);
        if (principal != null) {
            webSocketSessions.put(principal.getName(), session);
        } else {
            session.close();
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        Principal principal = session.getPrincipal();
        super.afterConnectionClosed(session, status);

        if (principal != null) {
            webSocketSessions.remove(principal.getName());
        }
    }

    @SneakyThrows
    //websocat ws://localhost:8080/post/feed/posted --header "Authorization: Bearer {}"
    public void sendToUser(String userId, PostDto postDto) {
        WebSocketSession session = webSocketSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String postJson = objectMapper.writeValueAsString(postDto);
                session.sendMessage(new TextMessage(postJson));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
