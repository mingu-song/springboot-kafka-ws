package kafkaws.controller;

import kafkaws.broker.Sender;
import kafkaws.config.WebSocketEvent;
import kafkaws.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final Sender sender;
    private final WebSocketEvent webSocketEvent;

    @MessageMapping("/chat.send-message")
    public void sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setSessionId(headerAccessor.getSessionId());
        sender.send("messaging", chatMessage);
        log.info("[<<<] (kafka) Sending message to /topic/public for OTHERS : {}", chatMessage);
        webSocketEvent.sendMessage(chatMessage);
        log.info("[<<<] (ws) Message sent to /topic/public for ME : {}", chatMessage);
    }

    @MessageMapping("/chat.add-user")
    public Message addUser(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        if (headerAccessor.getSessionAttributes() != null) {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        }
        webSocketEvent.sendMessage(chatMessage);
        return chatMessage;
    }
}
