package com.epam.repository;

import com.epam.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
