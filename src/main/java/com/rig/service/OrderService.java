package com.rig.service;

import com.rig.model.RIGOrderRequest;
import com.rig.model.entity.Order;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderService {

    void placeOrder(RIGOrderRequest request);

    OrderDetailsServiceResponse getOrderDetails(UUID orderId);

    List<Order> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
}
