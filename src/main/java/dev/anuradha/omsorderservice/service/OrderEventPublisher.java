package dev.anuradha.omsorderservice.service;

import dev.anuradha.omsorderservice.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final SqsTemplate sqsTemplate;

    @Value("${sqs.order-created-queue}")
    private String queueName;

    public void publishOrderCreated(OrderCreatedEvent event){
        log.info("Publishing ORDER_CREATED event for order{}", event.getOrderId());
        sqsTemplate.send(queueName, event);
    }

}
