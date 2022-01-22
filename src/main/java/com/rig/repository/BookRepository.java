package com.rig.repository;

import com.rig.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Book findByRowId(UUID rowId);
}
