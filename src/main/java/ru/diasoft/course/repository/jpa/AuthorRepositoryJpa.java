package ru.diasoft.course.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.domain.Author;
import ru.diasoft.course.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(a -> em.remove(em.contains(a) ? a : em.merge(a)));
    }
}


