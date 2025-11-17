package ru.diasoft.course.dto;

public record BookRequest(String title, Long authorId, Long genreId) {
}
