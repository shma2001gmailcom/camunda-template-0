package org.misha.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.misha.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageSender {
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final MessageChannel output;

    public MessageSender(MessageChannel output) {
        this.output = output;
    }

    public void send(Message<?> m) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonMessage = mapper.writeValueAsString(m);
            log.error("\n\n\n\n***" + jsonMessage);
            output.send(MessageBuilder.withPayload(jsonMessage)
                    .setHeader("messageType", m.getMessageType())
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
        log.error("\n\n\n\n***" + " has been sent");
    }
}