package ru.diasoft.course.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.course.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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


