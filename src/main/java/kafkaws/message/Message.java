package kafkaws.message;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Message {
    private MessageType type;
    private String content;
    private String sender;
    private String sessionId;
}
