package com.example.myrestapi.comment;

import com.example.myrestapi.review.ReviewRepository;
import com.example.myrestapi.user.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public Comment addComment(Comment comment) {
        if (!userRepository.existsById(comment.getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        if (!reviewRepository.existsById(comment.getReview().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        }
    }
}
