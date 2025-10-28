package ru.diasoft.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.course.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}


