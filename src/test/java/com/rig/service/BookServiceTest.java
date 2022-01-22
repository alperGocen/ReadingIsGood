package com.rig.service;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.mock.BookMockGenerator;
import com.rig.model.RIGBookRequest;
import com.rig.model.entity.Book;
import com.rig.repository.BookRepository;
import com.rig.service.impl.BookServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookServiceImpl bookService;

    @Before
    public void setup() throws Exception {
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    public void testUpdateBookStock() {
        final Book book = BookMockGenerator.createBook();
        final int oldStock = book.getNumberInStock();

        when(bookRepository.findByRowId(book.getRowId())).thenReturn(book);

        bookService.updateBookStock(book.getRowId(), -5);

        final int stockDifference = oldStock - book.getNumberInStock();

        assertEquals(stockDifference, 5);
    }

    @Test
    public void whenSaveBookIsCalled_BookIsSaved() {
        final RIGBookRequest bookRequest = BookMockGenerator.createBookSaveRequest();

        bookService.saveBook(bookRequest);

        Mockito.verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void whenBookDoesNotExist_thenBackendException() {
        final BackendException exception = assertThrows(BackendException.class, () -> {
            bookService.updateBookStock(UUID.randomUUID(), -5);
        });

        assertEquals(exception.getErrorType().getCode(), ErrorType.BOOK_NOT_EXIST.getCode());
    }
}
