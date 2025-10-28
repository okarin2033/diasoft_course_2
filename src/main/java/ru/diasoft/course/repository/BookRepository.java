package ru.diasoft.course.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.course.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "book.withAuthorAndGenre", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    @EntityGraph(value = "book.withAuthorAndGenre", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(Long id);
}


