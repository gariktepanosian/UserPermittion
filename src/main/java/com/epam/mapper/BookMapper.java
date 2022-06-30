package com.epam.mapper;

import com.epam.dto.BookDto;
import com.epam.model.Book;

public class BookMapper {

    public static BookDto map(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setDate(book.getDate());
        bookDto.setName(book.getName());
        bookDto.setId(book.getId());
        bookDto.setAuthorId(book.getAuthorId());
        return bookDto;
    }

    public static Book mapBook(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setDate(bookDto.getDate());
        book.setAuthorId(bookDto.getAuthorId());
        return book;
    }
}
