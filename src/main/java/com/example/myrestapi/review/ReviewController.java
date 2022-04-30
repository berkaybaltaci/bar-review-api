package com.example.myrestapi.review;

import com.example.myrestapi.user.User;
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
    public List<ReviewDto> getReviews() {
        List<Review> reviewList = reviewService.getReviews();
        return reviewList.stream().map(reviewService::entityToDto).toList();
    }

    @GetMapping("/{id}")
    public ReviewDto getReview(@PathVariable Long id) {
        Review review = reviewService.getReview(id);
        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        return reviewService.entityToDto(reviewService.getReview(id));
    }

    @PostMapping
    public ReviewDto addReview(@RequestBody ReviewDto reviewDto) {
        if (userService.getUser(reviewDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        Review reviewInDb = reviewService.addReview(reviewService.dtoToEntity(reviewDto));
        reviewDto.setId(reviewInDb.getId());
        return reviewDto;
    }

    @PutMapping("/{id}")
    public ReviewDto updateReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        Review reviewInDb = reviewService.getReview(id);
        if (reviewInDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        if (userService.getUser(reviewDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        reviewDto.setId(id);
        reviewService.updateReview(reviewService.dtoToEntity(reviewDto));
        return reviewDto;
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        if (reviewService.getReview(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        reviewService.deleteReview(id);
    }
}
