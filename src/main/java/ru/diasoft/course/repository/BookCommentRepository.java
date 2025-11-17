package ru.diasoft.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import ru.diasoft.course.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    @EntityGraph(attributePaths = "book")
    List<BookComment> findByBookId(Long bookId);
    @EntityGraph(attributePaths = "book")
    Optional<BookComment> findById(Long id);
    long deleteByBookId(Long bookId);
}


