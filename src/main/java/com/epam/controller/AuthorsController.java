package com.epam.controller;


import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.mapper.AuthorMapper;
import com.epam.mapper.BookMapper;
import com.epam.model.Author;
import com.epam.repository.AuthorRepository;
import com.epam.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/authors")
public class AuthorsController {

    private final AuthorsService authorsService;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorsController(AuthorsService authorsService, AuthorRepository authorRepository) {
        this.authorsService = authorsService;
        this.authorRepository = authorRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto authorDtoCreate = authorsService.create(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(authorDtoCreate);
    }

    @GetMapping(value = "/{authorId}/books",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {

        List<BookDto> bookDtos = authorsService.findBooksByAuthor(authorId).stream()
                .map(BookMapper::map)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping(value = "/{authorId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getById(@PathVariable Long authorId) {
        Author author = authorsService.getById(authorId);
        AuthorDto dto = AuthorMapper.map(author);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAll() {
        List<Author> all = authorRepository.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        for (Author author : all) {
           authorDtos.add(AuthorMapper.map(author));
        }
        return ResponseEntity.ok(authorDtos);
    }

    @PutMapping(value = "/{authorId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> updateAuthors(@PathVariable Long authorId, @RequestBody AuthorDto authorDto) {
        authorDto.setId(authorId);
        AuthorDto updatingAuthors = authorsService.update(authorDto);
        return ResponseEntity.ok(updatingAuthors);
    }

    @DeleteMapping(value = "/{authorId}")
    public Map<String, Boolean> deleteAuthors(@PathVariable Long authorId) {
        Author authorById = authorsService.getById(authorId);
        authorsService.delete(authorById.getId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PatchMapping(value = "/{authorId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<AuthorDto> updateAuthorsFields(@PathVariable Long authorId, @RequestBody AuthorDto authorDto){
        authorDto.setId(authorId);
        AuthorDto authorUpdateFields = authorsService.updateField(authorDto);
        return ResponseEntity.status(HttpStatus.UPGRADE_REQUIRED).body(authorUpdateFields);
    }
}
