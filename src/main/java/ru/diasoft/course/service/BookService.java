package ru.diasoft.course.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.course.domain.Author;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.domain.Genre;
import ru.diasoft.course.repository.AuthorRepository;
import ru.diasoft.course.repository.BookRepository;
import ru.diasoft.course.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public List<Book> list() {
        return bookRepository.findAll();
    }

    public Book get(Long id) {
        return bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Book create(String title, Long authorId, Long genreId) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is blank");
        }
        Author author = authorRepository.findById(authorId).orElseThrow(NoSuchElementException::new);
        Genre genre = genreRepository.findById(genreId).orElseThrow(NoSuchElementException::new);
        return bookRepository.save(new Book(null, title, author, genre));
    }

    @Transactional
    public Book update(Long id, String title, Long authorId, Long genreId) {
        Book existing = get(id);
        if (title != null && !title.isBlank()) {
            existing.setTitle(title);
        }
        if (authorId != null) {
            Author author = authorRepository.findById(authorId).orElseThrow(NoSuchElementException::new);
            existing.setAuthor(author);
        }
        if (genreId != null) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(NoSuchElementException::new);
            existing.setGenre(genre);
        }
        return bookRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}


