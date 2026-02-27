package dev.anuradha.omsorderservice.controller;

import dev.anuradha.omsorderservice.dto.CreateOrderRequest;
import dev.anuradha.omsorderservice.model.Order;
import dev.anuradha.omsorderservice.repository.OrderRepository;
import dev.anuradha.omsorderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<Order> create(
            @RequestBody CreateOrderRequest orderRequest){
        return ResponseEntity.ok().body(orderService.createOrder(orderRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable UUID id){
        return ResponseEntity.ok(
                orderRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Order not found"))
        );
    }
}
