package org.misha.messages.impl;

import org.camunda.bpm.engine.RuntimeService;
import org.misha.domain.Message;
import org.misha.messages.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;


import static java.util.concurrent.CompletableFuture.*;

@Component
@EnableBinding(Source.class)
public class MessageSender implements Sender {
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    private final MessageChannel output;

    public MessageSender(@Qualifier("output") MessageChannel channel) {
        this.output = channel;

    }

    @Override
    public MessageChannel channel() {
        return output;
    }

    @Override
    public void send(Message<?> m) {
        log.debug("\n\n\n\n supply async");

        supplyAsync(() -> {
            Sender.super.send(m);
            return null;
        });
    }
}