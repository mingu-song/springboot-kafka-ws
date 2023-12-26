package kafkaws.controller;

import kafkaws.broker.Sender;
import kafkaws.config.WebSocketEvent;
import kafkaws.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommandController {

    private final Sender sender;
    private final WebSocketEvent webSocketEvent;

    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message) {
        sender.send("messaging", message);
        webSocketEvent.sendMessage(message);
    }
}
