package ru.diasoft.course.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.course.domain.Book;
import ru.diasoft.course.service.BookService;

import java.util.stream.Collectors;

@ShellComponent
public class BookCommands {
    private final BookService bookService;

    public BookCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = "book list", value = "List books")
    public String list() {
        return bookService.list().stream()
                .map(this::fmt)
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "book get", value = "Get book by id")
    public String get(@ShellOption(value = "--id") Long id) {
        Book b = bookService.get(id);
        return fmt(b);
    }

    @ShellMethod(key = "book create", value = "Create book")
    public String create(
            @ShellOption(value = "--title") String title,
            @ShellOption(value = "--authorId") Long authorId,
            @ShellOption(value = "--genreId") Long genreId
    ) {
        Book b = bookService.create(title, authorId, genreId);
        return fmt(b);
    }

    @ShellMethod(key = "book update", value = "Update book")
    public String update(
            @ShellOption(value = "--id") Long id,
            @ShellOption(value = "--title", defaultValue = ShellOption.NULL) String title,
            @ShellOption(value = "--authorId", defaultValue = ShellOption.NULL) Long authorId,
            @ShellOption(value = "--genreId", defaultValue = ShellOption.NULL) Long genreId
    ) {
        Book b = bookService.update(id, title, authorId, genreId);
        return fmt(b);
    }

    @ShellMethod(key = "book delete", value = "Delete book by id")
    public String delete(@ShellOption(value = "--id") Long id) {
        bookService.delete(id);
        return "OK";
    }

    private String fmt(Book b) {
        return b.getId() + ": " + b.getTitle() + " (authorId=" + b.getAuthor().getId() + ", genreId=" + b.getGenre().getId() + ")";
    }
}


