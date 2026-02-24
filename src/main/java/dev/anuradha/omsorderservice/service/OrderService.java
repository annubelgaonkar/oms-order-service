package dev.anuradha.omsorderservice.service;

import dev.anuradha.omsorderservice.dto.CreateOrderRequest;
import dev.anuradha.omsorderservice.model.Order;

public interface OrderService {

    Order createOrder(CreateOrderRequest request);
}
