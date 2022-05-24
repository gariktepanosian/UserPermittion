package com.epam.service;

import com.epam.model.Book;

import java.util.List;

public interface BookService {

    Book create(Book book);

    List<Book> read();

    void update(Book book);

    void delete(Book book);

}
