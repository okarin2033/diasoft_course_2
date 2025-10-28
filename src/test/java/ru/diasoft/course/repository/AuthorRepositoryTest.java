package ru.diasoft.course.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.diasoft.course.domain.Author;
 

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void listAndCrud() {
        List<Author> all = authorRepository.findAll();
        assertThat(all).isNotEmpty();

        Author created = authorRepository.save(new Author(null, "Тестовый автор"));
        assertThat(created.getId()).isNotNull();

        Author fetched = authorRepository.findById(created.getId()).orElseThrow();
        assertThat(fetched.getName()).isEqualTo("Тестовый автор");

        fetched.setName("Обновленный автор");
        Author updated = authorRepository.save(fetched);
        assertThat(updated.getName()).isEqualTo("Обновленный автор");

        authorRepository.deleteById(updated.getId());
        assertThat(authorRepository.findById(updated.getId())).isEmpty();
    }
}


