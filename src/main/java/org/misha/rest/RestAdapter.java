package org.misha.rest;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.glassfish.jersey.internal.guava.Preconditions.checkArgument;

@Component
@ConfigurationProperties
public class RestAdapter implements JavaDelegate {
    private final RestTemplate rest;
    private final String url;

    public RestAdapter(final RestTemplate rest, final @Value("${url}") String url) {
        this.rest = rest;
        this.url = url;
    }

    public void execute(final DelegateExecution ctx) throws Exception {
        Request request = new Request();
        request.amount = (Integer) ctx.getVariable("remainingAmount");
        Response response = rest.postForObject(url, request, Response.class);
        checkArgument(response != null, "null response");
        if (response.errorCode != null) {
            ctx.setVariable("errorCode", response.errorCode);
            throw new BpmnError("Error_PaymentError");
        }
        ctx.setVariable("paymentTransactionId", response.transactionId);
    }

    private static class Request {
        int amount;
    }

    private static class Response {
        String transactionId;
        String errorCode;
    }
}
