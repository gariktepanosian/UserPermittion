package com.epam.service;

import com.epam.mapper.AuthorMapper;
import com.epam.dto.AuthorDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.model.Author;
import com.epam.model.Book;
import com.epam.repository.AuthorRepository;
import com.epam.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class AuthorsServiceImpl implements AuthorsService {

    private AuthorRepository authorsRepository;
    private BookRepository bookRepository;

    @Autowired
    public AuthorsServiceImpl(AuthorRepository authorsRepository, BookRepository bookRepository) {
        this.authorsRepository = authorsRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Author author = AuthorMapper.mapAuthor(authorDto);
        Author savedAuthor = authorsRepository.save(author);
        return AuthorMapper.map(savedAuthor);
    }

    @Override
    public List<Author> getAll() {
        return authorsRepository.findAll();
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Author authorById = authorsRepository.findById(authorDto.getId()).orElseThrow(ResourceNotFoundException::new);
            authorById.setName(authorDto.getName());
            authorById.setLastName(authorDto.getLastName());
        return  AuthorMapper.map(authorsRepository.save(authorById));
    }

    @Override
    public AuthorDto updateField(AuthorDto authorDto) {
        Author authorById = authorsRepository.findById(authorDto.getId()).orElseThrow(ResourceNotFoundException::new);
        if (authorDto.getName()!=null) {
            authorById.setName(authorDto.getName());
        } else if (authorDto.getLastName()!=null) {
            authorById.setLastName(authorDto.getLastName());
        }
        return  AuthorMapper.map(authorsRepository.save(authorById));
    }

    @Override
    public void delete(Long id) {
        authorsRepository.deleteById(id);
    }

    @Override
    public Author getById(Long id) {
        return authorsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Collection<Book> findBooksByAuthor(Long id) {
        return bookRepository.findBooksByAuthor_idEquals(id);

    }
}
