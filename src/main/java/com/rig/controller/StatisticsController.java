package com.rig.controller;

import com.rig.api.StatisticsApi;
import com.rig.model.RIGUserStatisticsResponse;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Customer;
import com.rig.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class StatisticsController extends AbstractBaseController implements StatisticsApi {

    private final CustomerService customerService;

    public StatisticsController(final ModelMapper modelMapper, final CustomerService customerService) {
        super(modelMapper);
        this.customerService = customerService;
    }

    public ResponseEntity<RIGUserStatisticsResponse> getUserStatistics(final UUID customerId) {
        final List<StatisticsDto> userStatistics = customerService.getCustomerStatistics(customerId);

        final RIGUserStatisticsResponse response = getModelMapper().map(userStatistics, RIGUserStatisticsResponse.class);

        return ResponseEntity.ok(response);
    }
}
