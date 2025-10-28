package ru.diasoft.course.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.diasoft.course.repository.AuthorRepository;
import ru.diasoft.course.repository.GenreRepository;

import java.util.stream.Collectors;

@ShellComponent
public class ReferenceCommands {
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public ReferenceCommands(AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @ShellMethod(key = "ref authors", value = "List authors")
    public String authors() {
        return authorRepository.findAll().stream()
                .map(a -> a.getId() + ": " + a.getName())
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "ref genres", value = "List genres")
    public String genres() {
        return genreRepository.findAll().stream()
                .map(g -> g.getId() + ": " + g.getName())
                .collect(Collectors.joining("\n"));
    }
}


