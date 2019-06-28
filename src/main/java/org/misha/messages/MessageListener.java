package org.misha.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.misha.domain.Message;
import org.misha.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@EnableBinding(Sink.class)
class MessageListener implements JavaDelegate {
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    RuntimeService runtimeService;

    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').endsWith('Content')")
    public void orderPlacedReceived(Message<Order> message) throws IOException {
        Order order = message.getPayload();
        order.setId(UUID.randomUUID().toString());

        log.debug("\n\n---------------\n\nReceiver: New order placed: {}.", order);
    }

    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').endsWith('Content')")
    public void messageReceived(String messageJson) throws Exception {
        log.debug("\n\n---------------\n\nReceiver: json received={}", messageJson);
        final TypeReference<Message<JsonNode>> typeRef = new TypeReference<Message<JsonNode>>() {};
        final Message<JsonNode> message = new ObjectMapper().readValue(messageJson, typeRef);
        log.debug("\n\n---------------\n\nReceiver: messageType={}; traceId={}", message.getMessageType(), message.getTraceId());
    }

    @Override
    public void execute(final DelegateExecution execution) throws Exception {
        log.error("context variable : {}", runtimeService);
    }
}
