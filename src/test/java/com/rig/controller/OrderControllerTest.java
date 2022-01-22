package com.rig.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.controller.endpoints.Endpoints;
import com.rig.mock.CustomerMockGenerator;
import com.rig.mock.OrderMockGenerator;
import com.rig.model.RIGOrderRequest;
import com.rig.model.entity.Customer;
import com.rig.service.OrderService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class OrderControllerTest extends AbstractControllerTestBase {

    @MockBean
    private OrderService orderService;

    @Test
    public void whenNumberOfItemsSmallerThanOne_thenBackendException() throws Exception {
        final Customer customer = CustomerMockGenerator.createCustomer();
        final RIGOrderRequest request = OrderMockGenerator.createZeroItemOrderRequest(customer);

        doThrow(new BackendException(
                ErrorType.MINIMUM_ALLOWED_ORDER_EXCEEDED,
                ErrorMessages.ALLOWED_MINIMUM_ORDER_ERROR)).when(orderService).placeOrder(request);

        getMvc().perform(post(Endpoints.PLACE_ORDER)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BackendException))
                .andExpect(result -> assertTrue(((BackendException) result.getResolvedException()).getErrorType().getCode()
                        .equals(ErrorType.MINIMUM_ALLOWED_ORDER_EXCEEDED.getCode())));
    }
}
