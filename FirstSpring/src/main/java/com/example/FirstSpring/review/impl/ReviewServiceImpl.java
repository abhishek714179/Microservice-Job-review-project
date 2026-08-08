package com.example.FirstSpring.review.impl;

import com.example.FirstSpring.company.Company;
import com.example.FirstSpring.company.CompanyService;
import com.example.FirstSpring.review.Review;
import com.example.FirstSpring.review.ReviewRepository;
import com.example.FirstSpring.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
private CompanyService companyService;
    public ReviewServiceImpl(ReviewRepository reviewRepository,CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService=companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews=reviewRepository.findByCompanyId(companyId);
        return reviews;
    }

    @Override
    public boolean addReview(Long companyId, Review review) {
        Company company=companyService.getCompanyById(companyId);
        if (company != null) {

            review.setCompany(company);
            reviewRepository.save(review);
            return true;

        }

        return false;
    }

    @Override
    public Review getReview(Long companyId, Long ReviewId) {
        List<Review> reviews=  reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review->review.getId().equals(ReviewId))
                        .findFirst().orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        if(companyService.getCompanyById(companyId)!=null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!=null&&reviewRepository.existsById(reviewId)){
    Review review=reviewRepository.findById(reviewId).orElse(null);
    Company company=review.getCompany();
    company.getReviews().remove(review);
    review.setCompany(null);
    companyService.updateCompany(company,companyId);
    reviewRepository.deleteById(reviewId);
    return true;
        }
        return false;
    }
}
