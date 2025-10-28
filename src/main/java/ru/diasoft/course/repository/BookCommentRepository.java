package ru.diasoft.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.diasoft.course.domain.BookComment;

import java.util.List;

public interface BookCommentRepository extends JpaRepository<BookComment, Long> {
    List<BookComment> findByBookId(Long bookId);
    long deleteByBookId(Long bookId);
}


