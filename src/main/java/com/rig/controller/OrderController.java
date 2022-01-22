package com.rig.controller;

import com.rig.api.OrderApi;
import com.rig.model.RIGOrderDetailsResponse;
import com.rig.model.RIGOrderRequest;
import com.rig.model.RIGOrderResponse;
import com.rig.model.RIGOrdersResponse;
import com.rig.model.entity.Order;
import com.rig.service.OrderService;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController extends AbstractBaseController implements OrderApi {

    private final OrderService orderService;

    public OrderController(final ModelMapper modelMapper, final OrderService orderService) {
        super(modelMapper);
        this.orderService = orderService;
    }

    public ResponseEntity<RIGOrderResponse> saveOrder(final RIGOrderRequest request) {
        final RIGOrderResponse response = new RIGOrderResponse();

        orderService.placeOrder(request);

        response.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RIGOrderDetailsResponse> getOrderDetails(final UUID orderId) {
        final OrderDetailsServiceResponse serviceResponse = orderService.getOrderDetails(orderId);

        final RIGOrderDetailsResponse response = getModelMapper().map(serviceResponse, RIGOrderDetailsResponse.class);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RIGOrdersResponse> getOrdersBetweenDates(final String startDate, final String endDate) {
        final List<Order> orders = orderService.getOrdersBetweenDates(
                LocalDate.parse(startDate).atStartOfDay(),
                LocalDate.parse(endDate).atStartOfDay());

        final RIGOrdersResponse response = getModelMapper().map(orders, RIGOrdersResponse.class);

        return ResponseEntity.ok(response);
    }
}
