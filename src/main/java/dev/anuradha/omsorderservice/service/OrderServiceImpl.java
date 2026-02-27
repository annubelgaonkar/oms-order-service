package dev.anuradha.omsorderservice.service;

import dev.anuradha.omsorderservice.client.InventoryClient;
import dev.anuradha.omsorderservice.client.ProductClient;
import dev.anuradha.omsorderservice.dto.CreateOrderRequest;
import dev.anuradha.omsorderservice.dto.OrderCreatedEvent;
import dev.anuradha.omsorderservice.dto.OrderItemRequest;
import dev.anuradha.omsorderservice.dto.ProductResponse;
import dev.anuradha.omsorderservice.model.Order;
import dev.anuradha.omsorderservice.model.OrderItem;
import dev.anuradha.omsorderservice.model.OrderStatus;
import dev.anuradha.omsorderservice.repository.OrderItemRepository;
import dev.anuradha.omsorderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderEventPublisher eventPublisher;

    @Transactional
    @Override
    public Order createOrder(CreateOrderRequest orderRequest){

        BigDecimal totalAmount = BigDecimal.ZERO;

        //1. validate products + calculate total amount
        for(OrderItemRequest item : orderRequest.getItems()){
            ProductResponse product = productClient.getProduct(item.getProductId());

            if(product == null || Boolean.FALSE.equals(product.getActive())){
                throw new RuntimeException("Invalid product: " + item.getProductId());
            }

            totalAmount = totalAmount.add(
                    product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            );
        }
        //reserve quantity
        for(OrderItemRequest item : orderRequest.getItems()){
            boolean reserved = inventoryClient.reserveStock(item.getProductId(), item.getQuantity());

            if(!reserved){
                throw new RuntimeException(
                        "Insufficient inventory for product: "+item.getProductId()
                );
            }
        }

        //create order
        Order order = Order.builder()
                .userId(orderRequest.getUserId())
                .totalAmount(totalAmount)
                .status(OrderStatus.PAYMENT_PENDING)
                .build();

        Order savedOrder = orderRepository.save(order);

        //save order items
        for(OrderItemRequest item : orderRequest.getItems()){
            ProductResponse product = productClient.getProduct(item.getProductId());

            OrderItem orderItem = OrderItem.builder()
                    .orderId(savedOrder.getId())
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);
        }

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                        .orderId(savedOrder.getId())
                        .userId(savedOrder.getUserId())
                        .amount(savedOrder.getTotalAmount())
                        .build();

        eventPublisher.publishOrderCreated(event);

        log.info("Order created successfully: {}" + savedOrder.getId());

        return savedOrder;
    }

}
