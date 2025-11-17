package ru.diasoft.course.repository;

import ru.diasoft.course.domain.BookComment;

import java.util.Optional;

public interface BookCommentRepository {
    Optional<BookComment> findById(Long id);
    BookComment save(BookComment comment);
    void deleteById(Long id);
}
