package ru.diasoft.course.repository;

import ru.diasoft.course.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);
    Genre save(Genre genre);
    void deleteById(Long id);
}


