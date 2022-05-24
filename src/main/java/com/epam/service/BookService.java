package com.epam.service;

import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.model.Author;
import com.epam.model.Book;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookService {

    BookDto getById(Long id);

    BookDto create(BookDto bookDto);

    List<BookDto> getAll();

    BookDto update(BookDto bookDto);

    String delete(Long id);



    BookDto updateField(BookDto bookDto);

}
