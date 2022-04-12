package com.example.myrestapi.service;

import com.example.myrestapi.entity.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getReviews();
    Review getReview(Long id);
}
