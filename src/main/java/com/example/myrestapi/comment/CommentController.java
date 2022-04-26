package com.example.myrestapi.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<Comment> getComments() {
        return commentService.getComments();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @PostMapping
    public Comment addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        if (commentService.getComment(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        commentService.deleteComment(id);
    }
}
