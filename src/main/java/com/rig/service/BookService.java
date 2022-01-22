package com.rig.service;

import com.rig.model.RIGBookRequest;

import java.util.UUID;

public interface BookService {

    void saveBook(RIGBookRequest request);

    void updateBookStock(UUID bookId, int stockChange);
}
