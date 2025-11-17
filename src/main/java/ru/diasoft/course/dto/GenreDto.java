package ru.diasoft.course.dto;

import ru.diasoft.course.domain.Genre;

public record GenreDto(Long id, String name) {
    public static GenreDto from(Genre g) {
        return new GenreDto(g.getId(), g.getName());
    }
}
