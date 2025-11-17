package ru.diasoft.course.dto;

import ru.diasoft.course.domain.Book;

public record BookDto(Long id, String title, Long authorId, String authorName, Long genreId, String genreName) {
    public static BookDto from(Book b) {
        return new BookDto(
                b.getId(),
                b.getTitle(),
                b.getAuthor() != null ? b.getAuthor().getId() : null,
                b.getAuthor() != null ? b.getAuthor().getName() : null,
                b.getGenre() != null ? b.getGenre().getId() : null,
                b.getGenre() != null ? b.getGenre().getName() : null
        );
    }
}
