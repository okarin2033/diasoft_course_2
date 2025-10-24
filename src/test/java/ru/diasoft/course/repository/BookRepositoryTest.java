package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.diasoft.course.repository.jpa.BookRepositoryJpa;
import ru.diasoft.course.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findAllWithGraph() {
        assertThat(bookRepository.findAll()).isNotEmpty();
    }
}


