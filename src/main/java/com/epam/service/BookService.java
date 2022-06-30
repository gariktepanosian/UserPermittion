package com.epam.service;

import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;

import java.util.List;

public interface BookService {

    List<AuthorDto> getAuthorsById(Long id);

    BookDto getById(Long id);

    BookDto create(BookDto bookDto);

    List<BookDto> getAll();

    BookDto update(BookDto bookDto);

    String delete(Long id);

    BookDto updateField(BookDto bookDto);

}
