package com.example.FirstSpring.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long CompanyId);
    boolean addReview(Long companyId,Review review);
    Review getReview(Long companyId,Long ReviewId);
   boolean updateReview(Long companyId,Long reviewId,Review review);
   boolean deleteReview(Long companyId,Long reviewId);
}
