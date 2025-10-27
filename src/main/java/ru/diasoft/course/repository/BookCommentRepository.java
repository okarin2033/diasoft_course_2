package ru.diasoft.course.repository;

import ru.diasoft.course.domain.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentRepository {
    List<BookComment> findByBookId(Long bookId);
    Optional<BookComment> findById(Long id);
    BookComment save(BookComment comment);
    void deleteById(Long id);
}


