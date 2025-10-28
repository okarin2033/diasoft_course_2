package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookCommentRepositoryTest {

    @Autowired
    private BookCommentRepository commentRepository;

    @Test
    void findByBookId() {
        assertThat(commentRepository.findByBookId(1L)).isNotEmpty();
    }
}


