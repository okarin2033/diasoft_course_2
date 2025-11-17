package ru.diasoft.course.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.diasoft.course.dto.BookDto;
import ru.diasoft.course.dto.BookRequest;
import ru.diasoft.course.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDto> list() {
        return bookService.list().stream().map(BookDto::from).toList();
    }

    @GetMapping("/{id}")
    public BookDto get(@PathVariable Long id) {
        return BookDto.from(bookService.get(id));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookRequest request) {
        var created = bookService.create(request.title(), request.authorId(), request.genreId());
        return ResponseEntity.status(HttpStatus.CREATED).body(BookDto.from(created));
    }

    @PutMapping("/{id}")
    public BookDto update(@PathVariable Long id, @RequestBody BookRequest request) {
        var updated = bookService.update(id, request.title(), request.authorId(), request.genreId());
        return BookDto.from(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
