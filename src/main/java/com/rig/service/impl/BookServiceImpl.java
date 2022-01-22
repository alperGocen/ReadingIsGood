package com.rig.service.impl;

import com.rig.config.exception.BackendException;
import com.rig.config.exception.ErrorType;
import com.rig.constant.ErrorMessages;
import com.rig.model.RIGBookRequest;
import com.rig.model.entity.Book;
import com.rig.repository.BookRepository;
import com.rig.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public void saveBook(final RIGBookRequest request) {
        final Book book = new Book();

        book.setName(request.getName());
        book.setPrice(request.getPrice());
        book.setNumberInStock(request.getAddedStock());

        bookRepository.save(book);
    }

    public void updateBookStock(final UUID bookId, final int stockChange) {
        final Book bookToUpdate = bookRepository.findByRowId(bookId);

        if (Objects.isNull(bookToUpdate)) {
            throw new BackendException(ErrorType.BOOK_NOT_EXIST, ErrorMessages.BOOK_NOT_EXIST);
        }

        // stock change will be a negative number in case of a sale
        int numberInStock = bookToUpdate.getNumberInStock();
        numberInStock = numberInStock + stockChange;

        bookToUpdate.setNumberInStock(numberInStock);

        bookRepository.save(bookToUpdate);
    }
}
