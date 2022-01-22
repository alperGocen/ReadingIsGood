package com.rig.converter;

import com.rig.model.RIGOrder;
import com.rig.model.RIGOrdersResponse;
import com.rig.model.RIGUserStatistic;
import com.rig.model.RIGUserStatisticsResponse;
import com.rig.model.dto.StatisticsDto;
import com.rig.model.entity.Order;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RIGUserStatisticsResponseConverter implements Converter<ArrayList<StatisticsDto>, RIGUserStatisticsResponse> {

    @Override
    public RIGUserStatisticsResponse convert(final MappingContext<ArrayList<StatisticsDto>,
            RIGUserStatisticsResponse> mappingContext) {

        final RIGUserStatisticsResponse clientResponse = new RIGUserStatisticsResponse();
        final List<StatisticsDto> userStatistics = mappingContext.getSource();
        final List<RIGUserStatistic> statisticsList = new ArrayList<>();

        for (final StatisticsDto stat : userStatistics) {
            final RIGUserStatistic clientStatistic = new RIGUserStatistic();

            clientStatistic.setMonth(stat.getMonth());
            clientStatistic.setYear(stat.getYear());
            clientStatistic.setTotalBookCount((int) stat.getTotalBookCount());
            clientStatistic.setTotalOrderCount((int) stat.getTotalOrderCount());
            clientStatistic.setTotalPurchasedAmount(stat.getTotalPurchasedAmount());

            statisticsList.add(clientStatistic);
        }

        clientResponse.setStatisticList(statisticsList);
        clientResponse.setSuccess(true);

        return clientResponse;
    }
}
