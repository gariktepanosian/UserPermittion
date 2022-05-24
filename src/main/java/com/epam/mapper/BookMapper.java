package com.epam.mapper;

import com.epam.dto.BookDto;
import com.epam.model.Book;

public class BookMapper {

    public static BookDto map(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setBookDate(book.getBookDate());
        bookDto.setName(book.getName());
        bookDto.setId(book.getId());
        return bookDto;
    }

    public static Book mapBook(BookDto bookDto){
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setName(bookDto.getName());
        book.setBookDate(bookDto.getBookDate());
        return book;
    }
}
