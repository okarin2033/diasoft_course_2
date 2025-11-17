package ru.diasoft.course.service;

import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.course.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> list();

    Book get(Long id);

    @Transactional
    Book create(String title, Long authorId, Long genreId);

    @Transactional
    Book update(Long id, String title, Long authorId, Long genreId);

    @Transactional
    void delete(Long id);
}
