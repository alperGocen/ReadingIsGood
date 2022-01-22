package com.rig.controller;

import com.rig.api.CustomerApi;
import com.rig.model.RIGCustomerRequest;
import com.rig.model.RIGCustomerResponse;
import com.rig.model.RIGOrdersResponse;
import com.rig.model.entity.Order;
import com.rig.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class CustomerController extends AbstractBaseController implements CustomerApi {

    private final CustomerService customerService;

    public CustomerController(final ModelMapper modelMapper, final CustomerService customerService) {
        super(modelMapper);
        this.customerService = customerService;
    }

    public ResponseEntity<RIGCustomerResponse> saveCustomer(final RIGCustomerRequest request) {
        final RIGCustomerResponse response = new RIGCustomerResponse();

        customerService.saveCustomer(request);

        response.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RIGOrdersResponse> getCustomerOrders(final UUID customerId) {
        final List<Order> customerOrders = customerService.getAllCustomerOrders(customerId);

        final RIGOrdersResponse response = getModelMapper().map(customerOrders, RIGOrdersResponse.class);

        return ResponseEntity.ok(response);
    }
}
