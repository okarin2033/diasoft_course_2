package ru.diasoft.course.dto;

import ru.diasoft.course.domain.BookComment;

public record BookCommentDto(Long id, Long bookId, String text) {
    public static BookCommentDto from(BookComment c) {
        return new BookCommentDto(
                c.getId(),
                c.getBook() != null ? c.getBook().getId() : null,
                c.getText()
        );
    }
}
