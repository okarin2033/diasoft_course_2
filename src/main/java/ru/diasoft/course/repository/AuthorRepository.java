package ru.diasoft.course.repository;

import ru.diasoft.course.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author save(Author author);
    void deleteById(Long id);
}


