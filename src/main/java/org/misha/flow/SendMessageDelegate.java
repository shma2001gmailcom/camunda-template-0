package org.misha.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.Message;
import org.misha.Order;
import org.misha.messages.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendMessageDelegate implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(SendMessageDelegate.class);
    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution context) throws Exception {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        SendMessageContent sendMessageContent = new SendMessageContent()
                .setRefId(order.getId());
        log.error("\n\n\n\n***" + sendMessageContent);
        messageSender.send(new Message<SendMessageContent>("SendMessageContent",
                sendMessageContent));
        log.error("\n\n\n\n*** has been sent");
    }
}
