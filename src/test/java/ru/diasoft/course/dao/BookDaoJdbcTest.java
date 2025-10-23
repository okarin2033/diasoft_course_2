package ru.diasoft.course.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import ru.diasoft.course.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@JdbcTest
@ComponentScan(basePackages = "ru.diasoft.course.dao.jdbc")
class BookDaoJdbcTest {

    @Autowired
    private BookDao bookDao;

    @Test
    void listAndCrud() {
        List<Book> all = bookDao.findAll();
        assertThat(all).isNotEmpty();

        Book created = bookDao.insert(new Book(null, "Тестовая книга", 1L, 1L));
        assertThat(created.getId()).isNotNull();

        Book fetched = bookDao.findById(created.getId()).orElseThrow();
        assertThat(fetched.getTitle()).isEqualTo("Тестовая книга");

        fetched.setTitle("Обновленная книга");
        boolean updated = bookDao.update(fetched);
        assertThat(updated).isTrue();
        assertThat(bookDao.findById(fetched.getId()).orElseThrow().getTitle()).isEqualTo("Обновленная книга");

        boolean deleted = bookDao.deleteById(fetched.getId());
        assertThat(deleted).isTrue();
        assertThat(bookDao.findById(fetched.getId())).isEmpty();
    }
}


