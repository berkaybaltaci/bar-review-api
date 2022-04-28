package com.example.myrestapi.comment;

import com.example.myrestapi.review.ReviewService;
import com.example.myrestapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final ReviewService reviewService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, ReviewService reviewService) {
        this.commentService = commentService;
        this.userService = userService;
        this.reviewService = reviewService;
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
        if (userService.getUser(comment.getUser().getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        if (reviewService.getReview(comment.getReview().getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
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
