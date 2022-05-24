package com.epam.repository;

import com.epam.model.Author;
import com.epam.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {

}
