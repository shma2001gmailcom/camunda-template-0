package org.misha.messages;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.spin.plugin.variable.SpinValues;
import org.misha.Message;
import org.misha.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.UUID;

@Component
@EnableBinding(Sink.class)
public class MessageListener {
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    private ProcessEngine camunda;

    @StreamListener(target = Sink.INPUT, condition = "(headers['messageType']?:'').endsWith('Content')")
    @Transactional
    public void orderPlacedReceived(Message<Order> message) throws IOException {
        Order order = message.getPayload();
        order.setId(UUID.randomUUID().toString());
        log.error("\n\n\n\n***New order placed, start flow. " + order);
        camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                .processInstanceBusinessKey(message.getTraceId())
                .setVariable("orderId", order.getId())
                .correlateWithResult();
    }

    @StreamListener(target = Sink.INPUT,
            condition = "(headers['messageType']?:'').endsWith('Content')")
    @Transactional
    public void messageReceived(String messageJson) throws Exception {
        Message<JsonNode> message = new ObjectMapper().readValue(messageJson,
                new TypeReference<Message<JsonNode>>() {});
        long correlatingInstances = camunda.getRuntimeService().createExecutionQuery()
                .messageEventSubscriptionName(message.getMessageType())
                .processInstanceBusinessKey(message.getTraceId())
                .count();
        if (correlatingInstances == 1) {
            log.error("\n\n\n\n***Correlating " + message + " to waiting flow instance");
            camunda.getRuntimeService().createMessageCorrelation(message.getMessageType())
                    .processInstanceBusinessKey(message.getTraceId())
                    .setVariable(
                            "PAYLOAD_" + message.getMessageType(),
                            SpinValues.jsonValue(message.getPayload().toString()).create())
                    .correlateWithResult();
        }
    }
}
