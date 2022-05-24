package com.epam.service;

import com.epam.dto.BookDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.mapper.BookMapper;
import com.epam.model.Author;
import com.epam.model.Book;
import com.epam.repository.AuthorRepository;
import com.epam.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BookDto map = BookMapper.map(book);
        return map;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = BookMapper.mapBook(bookDto);
        Book save = bookRepository.save(book);
        BookDto map = BookMapper.map(save);
        return map;
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Book book : bookList) {
            bookDtoList.add(BookMapper.map(book));
        }
        return bookDtoList;
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(ResourceNotFoundException::new);
        book.setName(bookDto.getName());
        book.setDate(bookDto.getDate());
        book.getAuthor().setId(bookDto.getAuthorId());
        Book save = bookRepository.save(book);
        BookDto map = BookMapper.map(save);
        return map;
    }

    @Override
    public String delete(Long id) {
        bookRepository.deleteById(id);
        String stringInfo ="The Book with the " +  id + "th ID was Deleted " + Boolean.TRUE;
        return stringInfo;
    }


    @Override
    public BookDto updateField(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(ResourceNotFoundException::new);
        if (bookDto.getName() != null) {
            book.setName(bookDto.getName());
        } else if (bookDto.getDate() != null) {
            book.setDate(bookDto.getDate());
        } else if (bookDto.getId() != null) {
            book.getAuthor().setId(bookDto.getAuthorId());
        }
        Book save = bookRepository.save(book);
        BookDto map = BookMapper.map(save);
        return map;
    }
}
