package ru.diasoft.course.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.dao.GenreDao;
import ru.diasoft.course.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Genre> MAPPER = new RowMapper<>() {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getLong("id"), rs.getString("name"));
        }
    };

    @Override
    public Optional<Genre> findById(Long id) {
        var list = jdbcTemplate.query("select id, name from genres where id = :id",
                Map.of("id", id), MAPPER);
        return list.stream().findFirst();
    }

    @Override
    public List<Genre> findAll() {
        return jdbcTemplate.query("select id, name from genres order by id", MAPPER);
    }

    @Override
    public Genre insert(Genre genre) {
        String sql = "insert into genres(name) values (:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource("name", genre.getName()), keyHolder, new String[]{"id"});
        Number key = keyHolder.getKey();
        if (key != null) {
            genre.setId(key.longValue());
        }
        return genre;
    }

    @Override
    public boolean update(Genre genre) {
        String sql = "update genres set name = :name where id = :id";
        int updated = jdbcTemplate.update(sql, Map.of("name", genre.getName(), "id", genre.getId()));
        return updated > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        int updated = jdbcTemplate.update("delete from genres where id = :id", Map.of("id", id));
        return updated > 0;
    }

    @Override
    public long count() {
        Long cnt = jdbcTemplate.queryForObject("select count(*) from genres", Map.of(), Long.class);
        return cnt == null ? 0 : cnt;
    }
}


