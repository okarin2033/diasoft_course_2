package ru.diasoft.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.course.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}


