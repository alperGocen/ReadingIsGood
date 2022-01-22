package com.rig.service;

import com.rig.mock.BookMockGenerator;
import com.rig.mock.CustomerMockGenerator;
import com.rig.mock.OrderMockGenerator;
import com.rig.model.RIGOrderRequest;
import com.rig.model.entity.Book;
import com.rig.model.entity.Customer;
import com.rig.model.entity.Order;
import com.rig.model.entity.OrderItem;
import com.rig.repository.BookRepository;
import com.rig.repository.CustomerRepository;
import com.rig.repository.OrderItemRepository;
import com.rig.repository.OrderRepository;
import com.rig.service.impl.BookServiceImpl;
import com.rig.service.impl.OrderServiceImpl;
import com.rig.service.serviceresponse.OrderDetailsServiceResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private BookRepository bookRepository;


    private BookServiceImpl bookService;
    private OrderServiceImpl orderService;

    @Before
    public void setup() {
        bookService = new BookServiceImpl(bookRepository);

        orderService = new OrderServiceImpl(
                orderRepository,
                customerRepository,
                orderItemRepository,
                bookRepository,
                bookService);
    }

    @Test
    public void whenPlacedOrder_thenOrderSaved() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final RIGOrderRequest request = OrderMockGenerator.createOrderSaveRequest(customer);

        final Book book1 = BookMockGenerator.createBook();
        final Book book2 = BookMockGenerator.createBook();

        when(bookRepository.findByRowId(request.getBookOrderList().get(0).getBookId()))
                .thenReturn(book1);

        when(bookRepository.findByRowId(request.getBookOrderList().get(1).getBookId()))
                .thenReturn(book2);

        orderService.placeOrder(request);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void whenPlacedOrder_thenEachBookSaved() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final RIGOrderRequest request = OrderMockGenerator.createOrderSaveRequest(customer);

        final Book book1 = BookMockGenerator.createBook();
        final Book book2 = BookMockGenerator.createBook();

        when(bookRepository.findByRowId(request.getBookOrderList().get(0).getBookId()))
                .thenReturn(book1);

        when(bookRepository.findByRowId(request.getBookOrderList().get(1).getBookId()))
                .thenReturn(book2);

        orderService.placeOrder(request);

        verify(bookRepository, times(request.getBookOrderList().size())).save(any(Book.class));
    }

    @Test
    public void whenPlacedOrder_thenOrderItemForEachBookSaved() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final RIGOrderRequest request = OrderMockGenerator.createOrderSaveRequest(customer);

        final Book book1 = BookMockGenerator.createBook();
        final Book book2 = BookMockGenerator.createBook();

        when(bookRepository.findByRowId(request.getBookOrderList().get(0).getBookId()))
                .thenReturn(book1);

        when(bookRepository.findByRowId(request.getBookOrderList().get(1).getBookId()))
                .thenReturn(book2);

        orderService.placeOrder(request);

        verify(orderItemRepository, times(request.getBookOrderList().size())).save(any(OrderItem.class));
    }

    @Test
    public void whenPlacedOrder_thenBookStocksUpdated() {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final RIGOrderRequest request = OrderMockGenerator.createOrderSaveRequest(customer);

        final Book book1 = BookMockGenerator.createBook();
        final Book book2 = BookMockGenerator.createBook();

        final int oldStock1 = book1.getNumberInStock();
        final int oldStock2 = book2.getNumberInStock();

        when(bookRepository.findByRowId(request.getBookOrderList().get(0).getBookId()))
                .thenReturn(book1);

        when(bookRepository.findByRowId(request.getBookOrderList().get(1).getBookId()))
                .thenReturn(book2);

        orderService.placeOrder(request);

        final int boughtAmount1 = request.getBookOrderList().get(0).getNumberInBasket();
        final int boughtAmount2 = request.getBookOrderList().get(1).getNumberInBasket();

        final int newStock1 = book1.getNumberInStock();
        final int newStock2 = book2.getNumberInStock();

        assertEquals(oldStock1 - newStock1, boughtAmount1);
        assertEquals(oldStock2 - newStock2, boughtAmount2);
    }

    @Test
    public void testGetOrderDetails() {
        final Order order = OrderMockGenerator.createOrder();
        final OrderDetailsServiceResponse serviceResponse = OrderMockGenerator.createOrderDetailsServiceResponse(order);

        assertEquals(order.getOrderNumber(), serviceResponse.getOrderNumber());
        assertEquals(order.getPrice(), serviceResponse.getTotalPrice());
    }
}
