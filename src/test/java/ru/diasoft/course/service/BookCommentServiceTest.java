package ru.diasoft.course.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.diasoft.course.domain.BookComment;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class BookCommentServiceTest {

    @Autowired
    private BookCommentService bookCommentService;

    @Test
    void createUpdateDeleteAndList() {
        BookComment c = bookCommentService.create(1L, "Тестовый комментарий");
        assertThat(c.getId()).isNotNull();
        BookComment loaded = bookCommentService.get(c.getId());
        assertThat(loaded.getText()).isEqualTo("Тестовый комментарий");
        BookComment updated = bookCommentService.update(c.getId(), "Обновленный текст");
        assertThat(updated.getText()).isEqualTo("Обновленный текст");
        List<BookComment> list = bookCommentService.findByBook(1L);
        assertThat(list).isNotEmpty();
        bookCommentService.delete(c.getId());
        assertThatThrownBy(() -> bookCommentService.get(c.getId())).isInstanceOf(NoSuchElementException.class);
    }
}
