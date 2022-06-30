package com.epam.controller;

import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/books")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        BookDto findBookById = bookService.getById(id);
        return ResponseEntity.ok(findBookById);
    }

    @GetMapping(value = "/{id}/author", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AuthorDto>> getAuthorsByBook(@PathVariable Long id) {
        List<AuthorDto> findAuthorsById = bookService.getAuthorsById(id);
        return ResponseEntity.ok(findAuthorsById);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> createBooks(@RequestBody BookDto bookDto) {
        BookDto createBook = bookService.create(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createBook);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> updateBooks(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        BookDto updateBook = bookService.update(bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateBook);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> updateBooksFields(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookDto.setId(id);
        BookDto updateBookFields = bookService.updateField(bookDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateBookFields);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        BookDto findBookById = bookService.getById(id);
        String deleteChecker = bookService.delete(findBookById.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleteChecker);
    }
}

