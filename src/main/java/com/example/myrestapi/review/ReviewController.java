package com.example.myrestapi.review;

import com.example.myrestapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, UserService userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping
    public List<Review> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/{id}")
    public Review getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        if (userService.getUser(review.getUser().getId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }

        return reviewService.addReview(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        if (reviewService.getReview(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        reviewService.deleteReview(id);
    }
}
