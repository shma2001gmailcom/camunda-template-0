package org.misha.flow;

public class SendMessageContent {
    private String refId;
    private String reason;
    private int amount;

    public String getRefId() {
        return refId;
    }

    public SendMessageContent setRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public SendMessageContent setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public SendMessageContent setAmount(int amount) {
        this.amount = amount;
        return this;
    }
}
