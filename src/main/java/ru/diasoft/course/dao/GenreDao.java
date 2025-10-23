package ru.diasoft.course.dao;

import ru.diasoft.course.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Optional<Genre> findById(Long id);
    List<Genre> findAll();
    Genre insert(Genre genre);
    boolean update(Genre genre);
    boolean deleteById(Long id);
    long count();
}


