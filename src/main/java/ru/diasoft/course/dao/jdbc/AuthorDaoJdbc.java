package ru.diasoft.course.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.dao.AuthorDao;
import ru.diasoft.course.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Author> MAPPER = new RowMapper<>() {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author(rs.getLong("id"), rs.getString("name"));
        }
    };

    @Override
    public Optional<Author> findById(Long id) {
        var list = jdbcTemplate.query("select id, name from authors where id = :id",
                Map.of("id", id), MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        return jdbcTemplate.query("select id, name from authors order by id", MAPPER);
    }

    @Override
    public Author insert(Author author) {
        String sql = "insert into authors(name) values (:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource("name", author.getName()), keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key != null) {
            author.setId(key.longValue());
        }
        return author;
    }

    @Override
    public boolean update(Author author) {
        String sql = "update authors set name = :name where id = :id";
        int updated = jdbcTemplate.update(sql, Map.of("name", author.getName(), "id", author.getId()));
        return updated > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        int updated = jdbcTemplate.update("delete from authors where id = :id", Map.of("id", id));
        return updated > 0;
    }

    @Override
    public long count() {
        Long cnt = jdbcTemplate.queryForObject("select count(*) from authors", Map.of(), Long.class);
        return cnt == null ? 0 : cnt;
    }
}


