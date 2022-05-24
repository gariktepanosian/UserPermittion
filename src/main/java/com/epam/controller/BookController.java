package com.epam.controller;

import com.epam.dto.AuthorDto;
import com.epam.dto.BookDto;
import com.epam.mapper.AuthorMapper;
import com.epam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/books")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //todo NullPointException BookMapper parseAuthorId
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> createBooks(@RequestBody BookDto bookDto) {
        BookDto bookCreate = bookService.create(bookDto);
        return ResponseEntity.ok(bookCreate);
    }
 //todo This is working
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        BookDto bookById = bookService.getById(id);
        return ResponseEntity.ok(bookById);
    }
//todo This is working
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }
//todo This is working
    @DeleteMapping(value = "/{id}")
    public String deleteBook(@PathVariable Long id) {
        BookDto bookById = bookService.getById(id);
        String delete = bookService.delete(bookById.getId());
        return delete;
    }


    //todo Write Put method
//    @PutMapping

    //todo Write Patch method
//    @PatchMapping

}

