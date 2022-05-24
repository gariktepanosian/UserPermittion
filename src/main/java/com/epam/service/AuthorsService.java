package com.epam.service;

import com.epam.dto.AuthorDto;
import com.epam.model.Author;
import com.epam.model.Book;

import java.util.Collection;
import java.util.List;

public interface AuthorsService {

    Author getById(Long id);

    AuthorDto create(AuthorDto authorDto);

    List<Author> getAll();

    AuthorDto update(AuthorDto authorDto);

    void delete(Long id);

    Collection<Book> findBooksByAuthor(Long id);

    AuthorDto updateField(AuthorDto authorDto);
}
