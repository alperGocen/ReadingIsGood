package com.rig.converter;

import com.rig.model.RIGOrder;
import com.rig.model.RIGOrdersResponse;
import com.rig.model.entity.Order;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RIGOrdersResponseConverter implements Converter<ArrayList<Order>, RIGOrdersResponse> {

    @Override
    public RIGOrdersResponse convert(final MappingContext<ArrayList<Order>,
                RIGOrdersResponse> mappingContext) {

        final RIGOrdersResponse clientResponse = new RIGOrdersResponse();
        final List<Order> customerOrders = mappingContext.getSource();
        final List<RIGOrder> rigOrders = new ArrayList<>();

        for (final Order order : customerOrders) {
            final RIGOrder rigOrder = new RIGOrder();

            rigOrder.setOrderNumber(order.getOrderNumber());
            rigOrder.setOrderCreatationDate(order.getCreateDate().toString());
            rigOrder.setOrderTotalPrice(order.getPrice());

            rigOrders.add(rigOrder);
        }

        clientResponse.setOrderList(rigOrders);
        clientResponse.setSuccess(true);

        return clientResponse;
    }
}
