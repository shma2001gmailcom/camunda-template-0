package org.misha.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Message<T> implements Serializable {
    private String messageType;
    private String id = UUID.randomUUID().toString();
    private String traceId = UUID.randomUUID().toString();
    private String sender = "Ca-Ka Message Sender";
    private Date timestamp = new Date();
    private String correlationId;
    private T payload;

    public Message() {
    }

    public Message(T payload) {
        messageType = payload.getClass().getSimpleName();
        correlationId = UUID.randomUUID().toString();
        this.payload = payload;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getId() {
        return id;
    }

    public Message<T> setId(String id) {
        this.id = id;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Message<T> setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("messageType", messageType)
                .append("id", id)
                .append("traceId", traceId)
                .append("sender", sender)
                .append("timestamp", timestamp)
                .append("correlationId", correlationId)
                .append("payload", payload)
                .toString();
    }
}

