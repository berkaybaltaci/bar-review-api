package com.example.myrestapi.comment;

import com.example.myrestapi.review.ReviewService;
import com.example.myrestapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    @PreAuthorize("isAuthenticated()")
    public CommentDto addComment(@RequestBody @Valid CommentDto commentDto) {
        if (reviewService.getReview(commentDto.getReviewId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        commentDto.setId(0L);
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Long userId = userService.findUserByName(userName).getId();
        commentDto.setUserId(userId);
        Comment commentInDb = commentService.addComment(commentService.dtoToEntity(commentDto));
        commentDto.setId(commentInDb.getId());
        return commentDto;
    }

    @PutMapping("/{id}")
    @PreAuthorize("principal == @commentServiceImpl.getComment(#id).user.name")
    public CommentDto updateComment(@PathVariable Long id, @RequestBody @Valid CommentDto commentDto) {
        Comment commentInDb = commentService.getComment(id);
        if (commentInDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        if (reviewService.getReview(commentDto.getReviewId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        commentDto.setId(id);
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Long userId = userService.findUserByName(userName).getId();
        commentDto.setUserId(userId);
        commentService.updateComment(commentService.dtoToEntity(commentDto));
        return commentDto;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("principal == @commentServiceImpl.getComment(#id).user.name")
    public void deleteComment(@PathVariable Long id) {
        if (commentService.getComment(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with the given id is not found.");
        }
        commentService.deleteComment(id);
    }
}
