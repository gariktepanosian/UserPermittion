package com.epam.service.impl;

import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.mapper.AuthorMapper;
import com.epam.mapper.BookMapper;
import com.epam.model.Author;
import com.epam.model.Book;
import com.epam.repository.AuthorRepository;
import com.epam.repository.BookRepository;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private String infoAboutBook;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository= authorRepository;
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        BookDto map = BookMapper.map(book);
        return map;
    }

    @Override
    public List<AuthorDto> getAuthorsById(Long id) {
        List<Author> authorsById = authorRepository.getAuthorsById(id);
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for (Author author : authorsById) {
            authorDtoList.add(AuthorMapper.map(author));
        }
        return authorDtoList;
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
        book.setAuthorId(bookDto.getAuthorId());
        Book save = bookRepository.save(book);
        BookDto map = BookMapper.map(save);
        return map;
    }

    @Override
    public String delete(Long id) {
        bookRepository.deleteById(id);
        infoAboutBook ="The Book with the " +  id + "th ID was Deleted " + Boolean.TRUE;
        return infoAboutBook;
    }


    @Override
    public BookDto updateField(BookDto bookDto) {
        Book book = bookRepository.findById(bookDto.getId()).orElseThrow(ResourceNotFoundException::new);
        if (bookDto.getName() != null) {
            book.setName(bookDto.getName());
        } else if (bookDto.getDate() != null) {
            book.setDate(bookDto.getDate());
        } else if (bookDto.getId() != null) {
            book.setAuthorId(bookDto.getAuthorId());
        }
        Book save = bookRepository.save(book);
        BookDto map = BookMapper.map(save);
        return map;
    }
}
