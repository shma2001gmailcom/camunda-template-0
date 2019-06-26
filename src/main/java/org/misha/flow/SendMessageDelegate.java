package org.misha.flow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.domain.Message;
import org.misha.domain.Order;
import org.misha.messages.impl.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class SendMessageDelegate implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(SendMessageDelegate.class);
    @Autowired
    private MessageSender messageSender;

    @Override
    public void execute(DelegateExecution context) throws Exception {
        final Message<SendMessageContent> msg = makeMessage();
        messageSender.send(msg);
        log.debug("\n---------------\nSender: content {} has been sent.\n", msg);
    }

    private Message<SendMessageContent> makeMessage() {
        final Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        final SendMessageContent content = new SendMessageContent();
        content.setRefId(order.getId());
        final Message<SendMessageContent> result = new Message<>();
        result.setPayload(content);
        result.setMessageType("SendMessageContent");
        result.setCorrelationId(UUID.randomUUID().toString());
        return result;
    }
}
