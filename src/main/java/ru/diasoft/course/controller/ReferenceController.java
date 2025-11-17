package ru.diasoft.course.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.diasoft.course.dto.AuthorDto;
import ru.diasoft.course.dto.GenreDto;
import ru.diasoft.course.repository.AuthorRepository;
import ru.diasoft.course.repository.GenreRepository;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReferenceController {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public ReferenceController(AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/authors")
    public List<AuthorDto> authors() {
        return authorRepository.findAll().stream().map(AuthorDto::from).toList();
    }

    @GetMapping("/genres")
    public List<GenreDto> genres() {
        return genreRepository.findAll().stream().map(GenreDto::from).toList();
    }
}
