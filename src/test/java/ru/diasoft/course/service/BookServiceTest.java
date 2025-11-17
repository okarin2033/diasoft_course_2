package ru.diasoft.course.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.course.domain.Book;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    void updateBook() {
        Book created = bookService.create("Tmp", 1L, 1L);
        Book updated = bookService.update(created.getId(), "Updated", 2L, 2L);
        assertThat(updated.getTitle()).isEqualTo("Updated");
        assertThat(updated.getAuthor().getId()).isEqualTo(2L);
        assertThat(updated.getGenre().getId()).isEqualTo(2L);
    }

    @Test
    void deleteBook() {
        Book created = bookService.create("ToDelete", 1L, 1L);
        Long id = created.getId();
        bookService.delete(id);
        assertThatThrownBy(() -> bookService.get(id)).isInstanceOf(NoSuchElementException.class);
    }
}


