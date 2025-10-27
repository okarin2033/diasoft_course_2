package ru.diasoft.course.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.diasoft.course.domain.BookComment;
import ru.diasoft.course.service.BookCommentService;

import java.util.stream.Collectors;

@ShellComponent
public class CommentCommands {
    private final BookCommentService commentService;

    public CommentCommands(BookCommentService commentService) {
        this.commentService = commentService;
    }

    @ShellMethod(key = "comment list", value = "List comments by book")
    public String list(@ShellOption(value = "--bookId") Long bookId) {
        return commentService.findByBook(bookId).stream()
                .map(c -> c.getId() + ": " + c.getText())
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "comment get", value = "Get comment by id")
    public String get(@ShellOption(value = "--id") Long id) {
        BookComment c = commentService.get(id);
        return c.getId() + ": " + c.getText();
    }

    @ShellMethod(key = "comment create", value = "Create comment")
    public String create(
            @ShellOption(value = "--bookId") Long bookId,
            @ShellOption(value = "--text") String text
    ) {
        BookComment c = commentService.create(bookId, text);
        return c.getId() + ": " + c.getText();
    }

    @ShellMethod(key = "comment update", value = "Update comment text")
    public String update(
            @ShellOption(value = "--id") Long id,
            @ShellOption(value = "--text") String text
    ) {
        BookComment c = commentService.update(id, text);
        return c.getId() + ": " + c.getText();
    }

    @ShellMethod(key = "comment delete", value = "Delete comment by id")
    public String delete(@ShellOption(value = "--id") Long id) {
        commentService.delete(id);
        return "OK";
    }
}


