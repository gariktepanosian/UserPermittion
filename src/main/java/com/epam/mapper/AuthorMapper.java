package com.epam.mapper;

import com.epam.dto.AuthorDto;
import com.epam.model.Author;

public class AuthorMapper {
    public static AuthorDto map(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setLastName(author.getLastName());
        authorDto.setName(author.getName());
        return authorDto;
    }

    public static Author mapAuthor(AuthorDto authorDto){
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setLastName(authorDto.getLastName());
        return author;
    }
}
