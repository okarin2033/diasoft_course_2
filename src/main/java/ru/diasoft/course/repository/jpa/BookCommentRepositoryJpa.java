package ru.diasoft.course.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.domain.BookComment;
import ru.diasoft.course.repository.BookCommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookCommentRepositoryJpa implements BookCommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<BookComment> findByBookId(Long bookId) {
        return em.createQuery("select c from BookComment c where c.book.id = :id", BookComment.class)
                .setParameter("id", bookId)
                .getResultList();
    }

    @Override
    public Optional<BookComment> findById(Long id) {
        return Optional.ofNullable(em.find(BookComment.class, id));
    }

    @Override
    public BookComment save(BookComment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(c -> em.remove(em.contains(c) ? c : em.merge(c)));
    }
}


