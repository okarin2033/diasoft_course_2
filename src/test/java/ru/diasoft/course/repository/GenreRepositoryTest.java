package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.course.domain.Genre;
 

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void listAndCrud() {
        List<Genre> all = genreRepository.findAll();
        assertThat(all).isNotEmpty();

        Genre created = genreRepository.save(new Genre(null, "Тестовый жанр"));
        assertThat(created.getId()).isNotNull();

        Genre fetched = genreRepository.findById(created.getId()).orElseThrow();
        assertThat(fetched.getName()).isEqualTo("Тестовый жанр");

        fetched.setName("Обновленный жанр");
        Genre updated = genreRepository.save(fetched);
        assertThat(updated.getName()).isEqualTo("Обновленный жанр");

        genreRepository.deleteById(updated.getId());
        assertThat(genreRepository.findById(updated.getId())).isEmpty();
    }
}


