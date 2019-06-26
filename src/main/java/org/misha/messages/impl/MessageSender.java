package org.misha.messages.impl;

import org.misha.messages.Sender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class MessageSender implements Sender {
    private final MessageChannel output;

    public MessageSender(@Qualifier("output") MessageChannel channel) {
        this.output = channel;
    }

    @Override
    public MessageChannel channel() {
        return output;
    }
}