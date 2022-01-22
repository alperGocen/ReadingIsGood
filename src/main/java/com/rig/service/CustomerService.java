package com.rig.service;

import com.rig.model.RIGCustomerRequest;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Order;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    void saveCustomer(RIGCustomerRequest request);

    List<Order> getAllCustomerOrders(UUID customerId);

    List<StatisticsDto> getCustomerStatistics(UUID customerId);
}
