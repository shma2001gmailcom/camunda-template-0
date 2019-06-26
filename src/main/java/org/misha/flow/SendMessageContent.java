package org.misha.flow;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SendMessageContent {
    private String refId;
    private String reason;

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("refId", refId)
                .append("reason", reason)
                .toString();
    }
}
