package com.rig.service.impl;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.Constant;
import com.rig.constant.ErrorMessages;
import com.rig.enums.OrderStatusEnum;
import com.rig.model.RIGBookOrder;
import com.rig.model.RIGOrderRequest;
import com.rig.model.entity.Book;
import com.rig.model.entity.Customer;
import com.rig.model.entity.Order;
import com.rig.model.entity.OrderItem;
import com.rig.repository.BookRepository;
import com.rig.repository.CustomerRepository;
import com.rig.repository.OrderItemRepository;
import com.rig.repository.OrderRepository;
import com.rig.service.BookService;
import com.rig.service.OrderService;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;

    @Transactional
    public void placeOrder(final RIGOrderRequest request) {
        final Order order = new Order();

        final Customer customer = customerRepository.findByRowId(request.getCustomerId());

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderStatusEnum(OrderStatusEnum.PLACED);
        order.setCustomer(customer);

        final List<Book> deactivatedBookItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (final RIGBookOrder bookOrder : request.getBookOrderList()) {
            if (bookOrder.getNumberInBasket() < NumberUtils.INTEGER_ONE) {
                throw new BackendException(
                        ErrorType.MINIMUM_ALLOWED_ORDER_EXCEEDED,
                        ErrorMessages.ALLOWED_MINIMUM_ORDER_ERROR);
            }

            final Book book = bookRepository.findByRowId(bookOrder.getBookId());

            if (bookOrder.getNumberInBasket() > book.getNumberInStock() - Constant.SECURE_STOCK_NUMBER) {
                throw new BackendException(ErrorType.STOCK_INSUFFICIENT, ErrorMessages.STOCK_INSUFFICIENT);
            }

            final OrderItem orderItem = new OrderItem();

            totalPrice = totalPrice.add(book.getPrice()
                    .multiply(new BigDecimal(bookOrder.getNumberInBasket())));

            orderItem.setBook(book);
            orderItem.setNumberInBasket(bookOrder.getNumberInBasket());

            orderItemRepository.save(orderItem);

            book.setActive(false);
            order.addOrderItem(orderItem);

            bookService.updateBookStock(bookOrder.getBookId(), bookOrder.getNumberInBasket() * -1);

            deactivatedBookItems.add(book);
        }

        order.setPrice(totalPrice);
        
        bookRepository.saveAll(deactivatedBookItems);
        orderRepository.save(order);
    }

    public OrderDetailsServiceResponse getOrderDetails(final UUID orderId) {
        final Order order = orderRepository.findByRowId(orderId);

        final OrderDetailsServiceResponse serviceResponse = new OrderDetailsServiceResponse();

        serviceResponse.setOrderNumber(order.getOrderNumber());
        serviceResponse.setOrderCreationDate(order.getCreateDate());
        serviceResponse.setTotalPrice(order.getPrice());
        serviceResponse.setOrderItemList(order.getOrderItems()
                .stream().map(orderItem -> orderItem.getBook()).collect(Collectors.toSet()));

        return serviceResponse;
    }

    public List<Order> getOrdersBetweenDates(final LocalDateTime startDate, final LocalDateTime endDate) {
        final List<Order> orders = orderRepository.findByStartAndEndDate(startDate, endDate);

        return orders;
    }
}
