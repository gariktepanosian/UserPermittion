package com.epam.service.impl;

import com.epam.mapper.AuthorMapper;
import com.epam.dto.AuthorDto;
import com.epam.exceptions.ResourceNotFoundException;
import com.epam.model.Author;
import com.epam.model.Book;
import com.epam.repository.AuthorRepository;
import com.epam.repository.BookRepository;
import com.epam.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
public class AuthorsServiceImpl implements AuthorsService {

    private AuthorRepository authorsRepository;
    private BookRepository bookRepository;
    private String infoAboutAuthor;

    @Autowired
    public AuthorsServiceImpl(AuthorRepository authorsRepository, BookRepository bookRepository) {
        this.authorsRepository = authorsRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Author author = AuthorMapper.mapAuthor(authorDto);
        return AuthorMapper.map(authorsRepository.save(author));
    }

    @Override
    public List<AuthorDto> getAll() {
        List<Author> authorList = authorsRepository.findAll();
        List<AuthorDto> authorDtoList = new ArrayList<>();
        for (Author author : authorList) {
           authorDtoList.add(AuthorMapper.map(author));
        }
        return authorDtoList;
    }
    @Transactional(isolation = Isolation.REPEATABLE_READ)
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
    public String delete(Long id) {
        authorsRepository.deleteById(id);
        infoAboutAuthor  ="The Author with the " +  id + "th ID was Deleted " + Boolean.TRUE;
        return infoAboutAuthor;
    }

    @Override
    public Author getById(Long id) {
        return authorsRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Collection<Book> findBooksByAuthor(Long id) {
        return bookRepository.findBooksByAuthorId(id);
    }
}
