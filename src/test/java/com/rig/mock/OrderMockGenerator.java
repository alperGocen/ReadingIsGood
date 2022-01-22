package com.rig.mock;

import com.rig.enums.OrderStatusEnum;
import com.rig.model.RIGBookOrder;
import com.rig.model.RIGOrder;
import com.rig.model.RIGOrderRequest;
import com.rig.model.entity.Book;
import com.rig.model.entity.Customer;
import com.rig.model.entity.Order;
import com.rig.model.entity.OrderItem;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@UtilityClass
public class OrderMockGenerator {

    public Order createOrder() {
        final Order order = new Order();

        order.setRowId(UUID.randomUUID());
        order.setPrice(new BigDecimal(100.00));
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatusEnum(OrderStatusEnum.PLACED);
        order.setCustomer(new Customer());

        return order;
    }

    public static List<Order> getCustomerOrderList(final Customer customer) {
        final List<Order> orders = new ArrayList<>();

        final Order order1 = new Order();

        order1.setPrice(new BigDecimal(100.00));
        order1.setOrderNumber(UUID.randomUUID().toString());
        order1.setOrderStatusEnum(OrderStatusEnum.PLACED);
        order1.setCustomer(customer);

        orders.add(order1);

        final Order order2 = new Order();

        order1.setPrice(new BigDecimal(120.00));
        order1.setOrderNumber(UUID.randomUUID().toString());
        order1.setOrderStatusEnum(OrderStatusEnum.PLACED);
        order1.setCustomer(customer);

        orders.add(order2);

        return orders;
    }

    public static RIGOrderRequest createOrderSaveRequest(final Customer customer) {
        final RIGOrderRequest request = new RIGOrderRequest();
        final List<RIGBookOrder> bookOrders = new ArrayList<>();

        bookOrders.add(createBookOrder(1));
        bookOrders.add(createBookOrder(1));

        request.setBookOrderList(bookOrders);
        request.setCustomerId(customer.getRowId());

        return request;
    }

    public RIGBookOrder createBookOrder(final int numberInBasket) {
        final RIGBookOrder bookOrder = new RIGBookOrder();

        bookOrder.setBookId(UUID.randomUUID());
        bookOrder.setNumberInBasket(numberInBasket);

        return bookOrder;
    }

    public OrderDetailsServiceResponse createOrderDetailsServiceResponse(final Order order) {
        final OrderDetailsServiceResponse serviceResponse = new OrderDetailsServiceResponse();
        final Set<Book> orderItemList = new HashSet<>();

        final Book book1 = BookMockGenerator.createBook();
        final Book book2 = BookMockGenerator.createBook();

        orderItemList.add(book1);
        orderItemList.add(book2);

        serviceResponse.setOrderItemList(orderItemList);
        serviceResponse.setOrderCreationDate(LocalDateTime.now());
        serviceResponse.setTotalPrice(order.getPrice());
        serviceResponse.setOrderNumber(order.getOrderNumber());

        return serviceResponse;
    }

    public RIGOrderRequest createZeroItemOrderRequest(final Customer customer) {
        final RIGOrderRequest request = new RIGOrderRequest();
        final List<RIGBookOrder> bookOrders = new ArrayList<>();

        final RIGBookOrder bookOrder = createBookOrder(NumberUtils.INTEGER_ZERO);

        bookOrders.add(bookOrder);

        request.setCustomerId(customer.getRowId());
        request.setBookOrderList(bookOrders);

        return request;
    }

    public OrderItem createOrderItem() {
        final OrderItem orderItem = new OrderItem();

        orderItem.setBook(BookMockGenerator.createBook());
        orderItem.setNumberInBasket(2);

        return orderItem;
    }
}
