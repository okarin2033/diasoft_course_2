package ru.diasoft.course.dao;

import ru.diasoft.course.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Optional<Book> findById(Long id);
    List<Book> findAll();
    Book insert(Book book);
    boolean update(Book book);
    boolean deleteById(Long id);
    long count();
}


