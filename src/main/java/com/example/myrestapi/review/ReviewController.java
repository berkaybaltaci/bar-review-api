package com.example.myrestapi.review;

import com.example.myrestapi.user.User;
import com.example.myrestapi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    @PreAuthorize("isAuthenticated()")
    public ReviewDto addReview(@RequestBody @Valid ReviewDto reviewDto) {
        if (userService.getUser(reviewDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        reviewDto.setId(0L);
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Long userId = userService.findUserByName(userName).getId();
        reviewDto.setUserId(userId);
        reviewService.addReview(reviewService.dtoToEntity(reviewDto));
        return reviewDto;
    }

    @PutMapping("/{id}")
    @PreAuthorize("principal == @reviewServiceImpl.getReview(#id).user.name")
    public ReviewDto updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDto reviewDto) {
        Review reviewInDb = reviewService.getReview(id);
        if (reviewInDb == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        if (userService.getUser(reviewDto.getUserId()) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the given id is not found.");
        }
        reviewDto.setId(id);
        String userName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Long userId = userService.findUserByName(userName).getId();
        reviewDto.setUserId(userId);
        reviewService.updateReview(reviewService.dtoToEntity(reviewDto));
        return reviewDto;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("principal == @reviewServiceImpl.getReview(#id).user.name")
    public void deleteReview(@PathVariable Long id) {
        if (reviewService.getReview(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with the given id is not found.");
        }
        reviewService.deleteReview(id);
    }
}
