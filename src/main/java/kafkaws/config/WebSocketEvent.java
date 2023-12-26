package kafkaws.config;

import kafkaws.message.Message;
import kafkaws.message.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEvent {

    private final SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public  void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = null;
        if (headerAccessor.getSessionAttributes() != null) {
            username = String.valueOf(headerAccessor.getSessionAttributes().get("username"));
        }
        if (StringUtils.hasText(username)) {
            sendMessage(Message.builder().type(MessageType.DISCONNECT).sender(username).build());
        }
        log.info("[---] User disconnected : {}", username);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = null;
        if (headerAccessor.getSessionAttributes() != null) {
            username = String.valueOf(headerAccessor.getSessionAttributes().get("username"));
        }
        // call first. so, don't know what is username
        log.info("[+++] User connected : {}", username);
    }

    public void sendMessage(Message message) {
        if (StringUtils.hasText(message.getSender()) && message.getType() != null) {
            messagingTemplate.convertAndSend("/topic/public", message);
        }
    }
}
