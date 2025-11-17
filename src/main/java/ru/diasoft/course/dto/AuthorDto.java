package ru.diasoft.course.dto;

import ru.diasoft.course.domain.Author;

public record AuthorDto(Long id, String name) {
    public static AuthorDto from(Author a) {
        return new AuthorDto(a.getId(), a.getName());
    }
}
