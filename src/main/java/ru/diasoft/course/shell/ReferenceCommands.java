package ru.diasoft.course.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.diasoft.course.dao.AuthorDao;
import ru.diasoft.course.dao.GenreDao;

import java.util.stream.Collectors;

@ShellComponent
public class ReferenceCommands {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public ReferenceCommands(AuthorDao authorDao, GenreDao genreDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @ShellMethod(key = "ref authors", value = "List authors")
    public String authors() {
        return authorDao.findAll().stream()
                .map(a -> a.getId() + ": " + a.getName())
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "ref genres", value = "List genres")
    public String genres() {
        return genreDao.findAll().stream()
                .map(g -> g.getId() + ": " + g.getName())
                .collect(Collectors.joining("\n"));
    }
}


