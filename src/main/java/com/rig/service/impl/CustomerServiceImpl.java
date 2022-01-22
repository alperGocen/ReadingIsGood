package com.rig.service.impl;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.model.RIGCustomerRequest;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Customer;
import com.rig.model.entity.Order;
import com.rig.repository.CustomerRepository;
import com.rig.repository.OrderRepository;
import com.rig.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public void saveCustomer(final RIGCustomerRequest request) {
        final Customer customer = new Customer();

        customer.setName(request.getName());
        customer.setSurname(request.getSurname());

        customerRepository.save(customer);
    }

    public List<Order> getAllCustomerOrders(final UUID customerId) {
        final List<Order> customerOrders = orderRepository.findByCustomerRowId(customerId);

        return customerOrders;
    }

    public List<StatisticsDto> getCustomerStatistics(final UUID customerId) {
        final Customer customer = customerRepository.findByRowId(customerId);

        if (Objects.isNull(customer)) {
            throw new BackendException(ErrorType.CUSTOMER_NOT_FOUND, ErrorMessages.CUSTOMER_NOT_FOUND);
        }

        return customerRepository.getCustomerStatistics(customer);
    }
}
