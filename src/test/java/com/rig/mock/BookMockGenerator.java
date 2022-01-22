package com.rig.mock;

import com.rig.model.RIGBookRequest;
import com.rig.model.RIGBookStockUpdateRequest;
import com.rig.model.entity.Book;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.UUID;

@UtilityClass
public class BookMockGenerator {

    public static Book createBook() {
        final Book book = new Book();

        book.setRowId(UUID.randomUUID());
        book.setNumberInStock(90);
        book.setPrice(new BigDecimal(100.00));
        book.setActive(true);

        return book;
    }

    public static RIGBookRequest createBookSaveRequest() {
        final RIGBookRequest bookRequest = new RIGBookRequest();

        bookRequest.setPrice(new BigDecimal(100.00));
        bookRequest.setAddedStock(20);
        bookRequest.setName("Angels and Demons");

        return bookRequest;
    }

    public static RIGBookStockUpdateRequest createStockUpdateRequest() {
        final RIGBookStockUpdateRequest bookRequest = new RIGBookStockUpdateRequest();

        bookRequest.setBookId(createBook().getRowId());
        bookRequest.setStockChange(-20);

        return bookRequest;
    }
}
