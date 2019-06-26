package org.misha.flow;

import org.misha.messages.impl.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.MessageChannel;

@Configuration
class FlowConfig {
    @Autowired
    private MessageChannel output;

    @Bean
    MessageSender messageSender() {
        return new MessageSender(channel());
    }

    @Bean
    @Qualifier("output")
    @Primary
    MessageChannel channel() {
        return output;
    }

}
