package com.epam.service;

import com.epam.exceptions.ResourceNotFoundException;
import com.epam.model.Book;
import com.epam.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Book> read() {
        create(new Book());
        return bookRepository.findAll();
    }

    @Override
    public void update(Book book) {
        Book byId = bookRepository.findById(book.getId()).orElseThrow(() -> new ResourceNotFoundException("User with given Id:" + book.getId() + " not found"));
        book.setId(byId.getId());
        bookRepository.save(book);
    }

    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
