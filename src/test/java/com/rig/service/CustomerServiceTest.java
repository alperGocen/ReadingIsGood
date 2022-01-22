package com.rig.service;

import com.rig.mock.CustomerMockGenerator;
import com.rig.mock.OrderMockGenerator;
import com.rig.model.RIGCustomerRequest;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Customer;
import com.rig.model.entity.Order;
import com.rig.repository.CustomerRepository;
import com.rig.repository.OrderRepository;
import com.rig.service.impl.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderRepository orderRepository;

    private CustomerServiceImpl customerService;

    @Before
    public void setup() {
        customerService = new CustomerServiceImpl(customerRepository, orderRepository);
    }

    @Test
    public void testSaveCustomers() {
        final RIGCustomerRequest request = CustomerMockGenerator.createCustomerSaveRequest();

        customerService.saveCustomer(request);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetAllCustomerOrders() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final List<Order> customerOrders = OrderMockGenerator.getCustomerOrderList(customer);

        when(orderRepository.findByCustomerRowId(customer.getRowId())).thenReturn(customerOrders);

        final List<Order> returnedOrders = customerService.getAllCustomerOrders(customer.getRowId());

        assertEquals(returnedOrders, customerOrders);
    }

    @Test
    public void testGetCustomerStatistics() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final List<StatisticsDto> customerStatistics = CustomerMockGenerator.createCustomerStatistics();

        when(customerRepository.findByRowId(customer.getRowId())).thenReturn(customer);
        when(customerRepository.getCustomerStatistics(customer)).thenReturn(customerStatistics);

        final List<StatisticsDto> returnedStatistics = customerService.getCustomerStatistics(customer.getRowId());
        int i = 0;

        assertEquals(customerStatistics.size(), returnedStatistics.size());

        for (i = 0; i < returnedStatistics.size(); i++) {
            assertEquals(
                    customerStatistics.get(i).getTotalPurchasedAmount(),
                    returnedStatistics.get(i).getTotalPurchasedAmount());
        }
    }
}
