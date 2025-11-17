package ru.diasoft.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.course.dto.BookCommentDto;
import ru.diasoft.course.dto.CommentCreateRequest;
import ru.diasoft.course.dto.CommentUpdateRequest;
import ru.diasoft.course.service.BookCommentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final BookCommentService bookCommentService;

    public CommentController(BookCommentService bookCommentService) {
        this.bookCommentService = bookCommentService;
    }

    @GetMapping("/books/{bookId}/comments")
    public List<BookCommentDto> listByBook(@PathVariable Long bookId) {
        return bookCommentService.findByBook(bookId).stream().map(BookCommentDto::from).toList();
    }

    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<BookCommentDto> create(@PathVariable Long bookId, @RequestBody CommentCreateRequest request) {
        var created = bookCommentService.create(bookId, request.text());
        return ResponseEntity.status(HttpStatus.CREATED).body(BookCommentDto.from(created));
    }

    @GetMapping("/comments/{id}")
    public BookCommentDto get(@PathVariable Long id) {
        return BookCommentDto.from(bookCommentService.get(id));
    }

    @PutMapping("/comments/{id}")
    public BookCommentDto update(@PathVariable Long id, @RequestBody CommentUpdateRequest request) {
        var updated = bookCommentService.update(id, request.text());
        return BookCommentDto.from(updated);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookCommentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
