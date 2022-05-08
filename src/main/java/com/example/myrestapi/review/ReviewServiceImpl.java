package com.example.myrestapi.review;

import com.example.myrestapi.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public Review addReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void updateReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public ReviewDto entityToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setText(review.getText());
        reviewDto.setId(review.getId());
        reviewDto.setUserId(review.getUser().getId());
        reviewDto.setRating(review.getRating());

        return reviewDto;
    }

    @Override
    public Review dtoToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setText(reviewDto.getText());
        review.setUser(userRepository.getById(reviewDto.getUserId()));
        review.setId(reviewDto.getId());
        review.setRating(reviewDto.getRating());

        return review;
    }
}
