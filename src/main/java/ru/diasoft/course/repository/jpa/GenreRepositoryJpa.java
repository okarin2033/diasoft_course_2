package ru.diasoft.course.repository.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.diasoft.course.domain.Genre;
import ru.diasoft.course.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void deleteById(Long id) {
        findById(id).ifPresent(g -> em.remove(em.contains(g) ? g : em.merge(g)));
    }
}


