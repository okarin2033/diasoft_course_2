package ru.diasoft.course.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;
import ru.diasoft.course.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ComponentScan(basePackages = {"ru.diasoft.course.dao.jdbc", "ru.diasoft.course.service"})
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    void createAndGet() {
        Book created = bookService.create("Сервисная книга", 1L, 1L);
        assertThat(created.getId()).isNotNull();
        assertThat(bookService.get(created.getId()).getTitle()).isEqualTo("Сервисная книга");
    }
}


