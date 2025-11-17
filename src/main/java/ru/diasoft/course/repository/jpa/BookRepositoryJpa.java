package ru.diasoft.course.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        return em.createQuery(
                "select b from Book b left join fetch b.author left join fetch b.genre",
                Book.class
        ).getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return em.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(b -> em.remove(em.contains(b) ? b : em.merge(b)));
    }
}



