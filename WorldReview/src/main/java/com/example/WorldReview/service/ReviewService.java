package com.example.WorldReview.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.WorldReview.dto.ReviewDTO;
import com.example.WorldReview.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ReviewDTO> getReviews(String countryCode) {
        return reviewRepository.findByCountry(countryCode);
    }

    public double getAverageRating(String countryCode) {
        Double avg = reviewRepository.getAverageRating(countryCode);
        return avg == null ? 0.0 : avg;
    }

    public ReviewDTO getReview(int id) {
        return reviewRepository.findById(id);
    }

    public void createReview(ReviewDTO review) {
        reviewRepository.save(review);
    }

    public void updateReview(ReviewDTO review) {
        reviewRepository.update(review);
    }

    public void deleteReview(int id) {
        reviewRepository.delete(id);
    }
    
}
