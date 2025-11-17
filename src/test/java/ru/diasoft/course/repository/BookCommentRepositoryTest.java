package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.course.repository.jpa.BookCommentRepositoryJpa;
import ru.diasoft.course.repository.jpa.BookRepositoryJpa;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.domain.BookComment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookCommentRepositoryJpa.class, BookRepositoryJpa.class})
class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository commentRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    void crud_operations() {
        Book book = bookRepository.findById(1L).orElseThrow();

        BookComment created = commentRepository.save(new BookComment(null, book, "Тестовый комментарий"));
        assertThat(created.getId()).isNotNull();

        BookComment fetched = commentRepository.findById(created.getId()).orElseThrow();
        assertThat(fetched.getText()).isEqualTo("Тестовый комментарий");

        fetched.setText("Обновленный комментарий");
        BookComment updated = commentRepository.save(fetched);
        assertThat(updated.getText()).isEqualTo("Обновленный комментарий");

        commentRepository.deleteById(updated.getId());
        assertThat(commentRepository.findById(updated.getId())).isEmpty();
    }
}
