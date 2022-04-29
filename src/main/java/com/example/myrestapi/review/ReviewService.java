package com.example.myrestapi.review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();

    Review getReview(Long id);

    Review addReview(Review review);

    void deleteReview(Long id);

    Review updateReview(Review review);
}
