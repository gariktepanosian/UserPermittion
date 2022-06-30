package com.epam.controller;


import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.mapper.AuthorMapper;
import com.epam.mapper.BookMapper;
import com.epam.model.Author;
import com.epam.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/authors")
public class AuthorsController {

    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(authorsService.getAll());
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        Author findAuthorById = authorsService.getById(id);
        AuthorDto createAuthorDtoOfAuthor = AuthorMapper.map(findAuthorById);
        return ResponseEntity.status(HttpStatus.OK).body(createAuthorDtoOfAuthor);
    }

    @GetMapping(value = "/{id}/books",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long id) {

        List<BookDto> bookDtoList = authorsService.findBooksByAuthor(id)
                .stream().map(BookMapper::map)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoList);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorDto createAuthor = authorsService.create(authorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createAuthor);
    }


    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> updateAuthors(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorDto updateAuthor = authorsService.update(authorDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateAuthor);
    }

    @PatchMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthorDto> updateAuthorsFields(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        authorDto.setId(id);
        AuthorDto updateAuthorFields = authorsService.updateField(authorDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateAuthorFields);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAuthors(@PathVariable Long id) {
        Author findAuthorById = authorsService.getById(id);
        String deleteChecker = authorsService.delete(findAuthorById.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteChecker);
    }
}
