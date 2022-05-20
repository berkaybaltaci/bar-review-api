package com.berkaybaltaci.myrestapi.review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review getReview(Long id);

    Review addReview(Review review);

    void deleteReview(Long id);

    void updateReview(Review review);

    ReviewDto entityToDto(Review review);

    Review dtoToEntity (ReviewDto reviewDto);
}
