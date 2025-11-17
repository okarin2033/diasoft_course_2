package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import ru.diasoft.course.repository.jpa.BookRepositoryJpa;
import ru.diasoft.course.repository.jpa.AuthorRepositoryJpa;
import ru.diasoft.course.repository.jpa.GenreRepositoryJpa;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.domain.Author;
import ru.diasoft.course.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@Import({BookRepositoryJpa.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private EntityManagerFactory emf;

    private Statistics statistics;

    @BeforeEach
    void initStats() {
        SessionFactory sf = emf.unwrap(SessionFactory.class);
        statistics = sf.getStatistics();
        statistics.clear();
    }

    @Test
    void findAll_noNPlusOne_on_author_and_genre() {
        assertThat(bookRepository.findAll()).isNotEmpty();
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1L);
    }

    @Test
    void crud_operations() {
        Author author = authorRepository.findById(1L).orElseThrow();
        Genre genre = genreRepository.findById(1L).orElseThrow();

        Book created = bookRepository.save(new Book(null, "Новая книга", author, genre));
        assertThat(created.getId()).isNotNull();

        Book loaded = bookRepository.findById(created.getId()).orElseThrow();
        assertThat(loaded.getTitle()).isEqualTo("Новая книга");
        assertThat(loaded.getAuthor().getId()).isEqualTo(author.getId());
        assertThat(loaded.getGenre().getId()).isEqualTo(genre.getId());

        loaded.setTitle("Обновленная книга");
        Book updated = bookRepository.save(loaded);
        assertThat(bookRepository.findById(updated.getId()).orElseThrow().getTitle())
                .isEqualTo("Обновленная книга");

        bookRepository.deleteById(updated.getId());
        assertThat(bookRepository.findById(updated.getId())).isEmpty();
    }
}
