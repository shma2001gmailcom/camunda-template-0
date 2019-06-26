package org.misha.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "OrderEntity")
public class Order {
    @Id
    protected String id;

    public String getId() {
        return id;
    }

    @JsonProperty("orderId")
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .toString();
    }
}
