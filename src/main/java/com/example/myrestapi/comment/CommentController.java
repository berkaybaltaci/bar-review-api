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
    public List<CommentDto> getComments() {
        List<Comment> commentList = commentService.getComments();
        return commentList.stream().map(commentService::entityToDto).toList();
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable Long id) {
        Comment comment = commentService.getComment(id);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        return commentService.entityToDto(commentService.getComment(id));
    }

    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto commentDto) {
        if (userService.getUser(commentDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        if (reviewService.getReview(commentDto.getReviewId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        Comment commentInDb = commentService.addComment(commentService.dtoToEntity(commentDto));
        commentDto.setId(commentInDb.getId());
        return commentDto;
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        Comment commentInDb = commentService.getComment(id);
        if (commentInDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        if (userService.getUser(commentDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        if (reviewService.getReview(commentDto.getReviewId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        commentDto.setId(id);
        commentService.updateComment(commentService.dtoToEntity(commentDto));
        return commentDto;
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        if (commentService.getComment(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        commentService.deleteComment(id);
    }
}
