package com.rig.converter;

import com.rig.model.RIGBook;
import com.rig.model.RIGOrder;
import com.rig.model.RIGOrderDetailsResponse;
import com.rig.model.entity.Book;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RIGOrderDetailsResponseConverter implements Converter<OrderDetailsServiceResponse, RIGOrderDetailsResponse> {

    @Override
    public RIGOrderDetailsResponse convert(final MappingContext<OrderDetailsServiceResponse,
            RIGOrderDetailsResponse> mappingContext) {

        final RIGOrderDetailsResponse response = new RIGOrderDetailsResponse();
        final OrderDetailsServiceResponse serviceResponse = mappingContext.getSource();

        final RIGOrder order = new RIGOrder();
        final List<RIGBook> bookList = new ArrayList<>();

        for (final Book book : serviceResponse.getOrderItemList()) {
            final RIGBook rigBook = new RIGBook();

            rigBook.setName(book.getName());
            rigBook.setPrice(book.getPrice());

            bookList.add(rigBook);
        }

        order.setOrderNumber(serviceResponse.getOrderNumber());
        order.setOrderCreatationDate(serviceResponse.getOrderCreationDate().toString());
        order.setOrderItemList(bookList);
        order.setOrderTotalPrice(serviceResponse.getTotalPrice());

        response.setOrderDetail(order);
        response.setSuccess(true);

        return response;
    }
}
