package com.rig.controller;

import com.rig.api.BookApi;
import com.rig.model.RIGBookRequest;
import com.rig.model.RIGBookResponse;
import com.rig.model.RIGBookStockUpdateRequest;
import com.rig.model.RIGBookStockUpdateResponse;
import com.rig.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController extends AbstractBaseController implements BookApi {

    private final BookService bookService;

    public BookController(final ModelMapper modelMapper, final BookService bookService) {
        super(modelMapper);
        this.bookService = bookService;
    }

    public ResponseEntity<RIGBookResponse> saveBook(final RIGBookRequest request) {
        final RIGBookResponse response = new RIGBookResponse();

        bookService.saveBook(request);

        response.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RIGBookStockUpdateResponse> updateBookStock(final RIGBookStockUpdateRequest request) {
        final RIGBookStockUpdateResponse response = new RIGBookStockUpdateResponse();

        bookService.updateBookStock(request.getBookId(), request.getStockChange());

        response.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

}
