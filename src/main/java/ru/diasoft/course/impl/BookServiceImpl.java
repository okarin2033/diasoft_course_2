package ru.diasoft.course.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diasoft.course.dao.AuthorDao;
import ru.diasoft.course.dao.BookDao;
import ru.diasoft.course.dao.GenreDao;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.service.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public List<Book> list() {
        return bookDao.findAll();
    }

    @Override
    public Book get(Long id) {
        return bookDao.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    @Override
    public Book create(String title, Long authorId, Long genreId) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is blank");
        }
        authorDao.findById(authorId).orElseThrow(NoSuchElementException::new);
        genreDao.findById(genreId).orElseThrow(NoSuchElementException::new);
        return bookDao.insert(new Book(null, title, authorId, genreId));
    }

    @Transactional
    @Override
    public Book update(Long id, String title, Long authorId, Long genreId) {
        Book existing = get(id);
        if (title != null && !title.isBlank()) {
            existing.setTitle(title);
        }
        if (authorId != null) {
            authorDao.findById(authorId).orElseThrow(NoSuchElementException::new);
            existing.setAuthorId(authorId);
        }
        if (genreId != null) {
            genreDao.findById(genreId).orElseThrow(NoSuchElementException::new);
            existing.setGenreId(genreId);
        }
        boolean ok = bookDao.update(existing);
        if (!ok) {
            throw new NoSuchElementException();
        }
        return existing;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        boolean ok = bookDao.deleteById(id);
        if (!ok) {
            throw new NoSuchElementException();
        }
    }
}
