package com.rig.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rig.controller.endpoints.Endpoints;
import com.rig.mock.BookMockGenerator;
import com.rig.model.RIGBookRequest;
import com.rig.model.RIGBookResponse;
import com.rig.model.RIGBookStockUpdateRequest;
import com.rig.service.BookService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends AbstractControllerTestBase {

    @MockBean
    private BookService bookService;


    @Test
    public void whenBookSaveCorrectly_thenResultIsOk() throws Exception {
        final RIGBookRequest request = BookMockGenerator.createBookSaveRequest();

        doNothing().when(bookService).saveBook(request);

        final int status = getMvc().perform(post(Endpoints.SAVE_BOOK)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, status);
    }

    @Test
    public void whenUpdateBookStock_thenResultIsOk() throws Exception {
        final RIGBookStockUpdateRequest request = BookMockGenerator.createStockUpdateRequest();

        doNothing().when(bookService).updateBookStock(request.getBookId(), request.getStockChange());

        final int status = getMvc().perform(post(Endpoints.SAVE_BOOK)
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, status);
    }
}
