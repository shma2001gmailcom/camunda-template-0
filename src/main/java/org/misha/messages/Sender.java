package org.misha.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.misha.domain.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

public interface Sender {

    default void send(Message<?> m) {
        String jsonMessage;
        try {
            jsonMessage = new ObjectMapper().writeValueAsString(m);
            channel().send(MessageBuilder.withPayload(jsonMessage)
                                         .setHeader("messageType", m.getMessageType())
                                         .build());
        } catch (Exception e) {
            throw new RuntimeException("Could not transform and send message due to: " + e.getMessage(), e);
        }
    }

    MessageChannel channel();
}
