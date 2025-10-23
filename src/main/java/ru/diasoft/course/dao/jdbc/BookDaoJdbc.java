package ru.diasoft.course.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.dao.BookDao;
import ru.diasoft.course.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Book> MAPPER = new RowMapper<>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getLong("author_id"),
                    rs.getLong("genre_id")
            );
        }
    };

    @Override
    public Optional<Book> findById(Long id) {
        var list = jdbcTemplate.query("select id, title, author_id, genre_id from books where id = :id",
                Map.of("id", id), MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select id, title, author_id, genre_id from books order by id", MAPPER);
    }

    @Override
    public Book insert(Book book) {
        String sql = "insert into books(title, author_id, genre_id) values (:title, :authorId, :genreId)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", book.getTitle())
                .addValue("authorId", book.getAuthorId())
                .addValue("genreId", book.getGenreId());
        jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key != null) {
            book.setId(key.longValue());
        }
        return book;
    }

    @Override
    public boolean update(Book book) {
        String sql = "update books set title = :title, author_id = :authorId, genre_id = :genreId where id = :id";
        int updated = jdbcTemplate.update(sql, Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthorId(),
                "genreId", book.getGenreId(),
                "id", book.getId()
        ));
        return updated > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        int updated = jdbcTemplate.update("delete from books where id = :id", Map.of("id", id));
        return updated > 0;
    }

    @Override
    public long count() {
        Long cnt = jdbcTemplate.queryForObject("select count(*) from books", Map.of(), Long.class);
        return cnt == null ? 0 : cnt;
    }
}


