package com.rig.mock;

import com.rig.model.RIGCustomerRequest;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Customer;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class CustomerMockGenerator {

    public static Customer createCustomer() {
        final Customer customer = new Customer();

        customer.setName("Customer1");
        customer.setRowId(UUID.randomUUID());
        customer.setRowId(UUID.randomUUID());

        return customer;
    }

    public static RIGCustomerRequest createCustomerSaveRequest() {
        final RIGCustomerRequest request = new RIGCustomerRequest();

        request.setName("CustomerName");
        request.setSurname("CustomerSurname");

        return request;
    }

    public static List<StatisticsDto> createCustomerStatistics() {
        final List<StatisticsDto> customerStatistics = new ArrayList<>();
        final StatisticsDto statisticsDto = new StatisticsDto(
                1,
                2022,
                25,
                35,
                new BigDecimal(260.00));

        customerStatistics.add(statisticsDto);

        return customerStatistics;
    }
}
