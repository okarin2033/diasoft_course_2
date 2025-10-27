package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.course.repository.jpa.BookCommentRepositoryJpa;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookCommentRepositoryJpa.class)
class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository commentRepository;

    @Test
    void findByBookId() {
        assertThat(commentRepository.findByBookId(1L)).isNotEmpty();
    }
}


