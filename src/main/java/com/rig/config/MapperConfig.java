package com.rig.config;

import com.rig.converter.RIGCustomerOrdersResponseConverter;
import com.rig.converter.RIGOrderDetailsResponseConverter;
import com.rig.converter.RIGUserStatisticsResponseConverter;
import com.rig.model.RIGUserStatisticsResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final RIGCustomerOrdersResponseConverter customerOrdersResponseConverter;
    private final RIGOrderDetailsResponseConverter orderDetailsResponseConverter;
    private final RIGUserStatisticsResponseConverter userStatisticsResponseConverter;

    @Bean
    public ModelMapper modelMapper() {
        final ModelMapper mapper = new ModelMapper();

        mapper.addConverter(customerOrdersResponseConverter);
        mapper.addConverter(orderDetailsResponseConverter);
        mapper.addConverter(userStatisticsResponseConverter);

        return mapper;
    }
}
