package ru.diasoft.course.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.domain.BookComment;
import ru.diasoft.course.repository.BookCommentRepository;
import ru.diasoft.course.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookCommentService {
    private final BookCommentRepository bookCommentRepository;
    private final BookRepository bookRepository;

    public BookCommentService(BookCommentRepository bookCommentRepository, BookRepository bookRepository) {
        this.bookCommentRepository = bookCommentRepository;
        this.bookRepository = bookRepository;
    }

    public List<BookComment> findByBook(Long bookId) {
        return bookCommentRepository.findByBookId(bookId);
    }

    public BookComment get(Long id) {
        return bookCommentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public BookComment create(Long bookId, String text) {
        Book book = bookRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        BookComment c = new BookComment(null, book, text);
        return bookCommentRepository.save(c);
    }

    @Transactional
    public BookComment update(Long id, String text) {
        BookComment c = get(id);
        if (text != null && !text.isBlank()) {
            c.setText(text);
        }
        return bookCommentRepository.save(c);
    }

    @Transactional
    public void delete(Long id) {
        bookCommentRepository.deleteById(id);
    }
}


