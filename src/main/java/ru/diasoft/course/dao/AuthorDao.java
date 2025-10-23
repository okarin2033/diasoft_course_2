package ru.diasoft.course.dao;

import ru.diasoft.course.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Optional<Author> findById(Long id);
    List<Author> findAll();
    Author insert(Author author);
    boolean update(Author author);
    boolean deleteById(Long id);
    long count();
}


