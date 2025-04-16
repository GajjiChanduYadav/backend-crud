package com.chandu.demo.jobportal.service;


import com.chandu.demo.jobportal.customException.CompanyNotFoundException;
import com.chandu.demo.jobportal.customException.ReviewNotFoundException;
import com.chandu.demo.jobportal.model.Company;
import com.chandu.demo.jobportal.model.Review;
import com.chandu.demo.jobportal.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService  companyService;

    public ReviewService(ReviewRepository reviewRepository,CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService=companyService;
    }

    public List<Review> getAllReviews(Long companyId){
        // This will throw an exception if the company doesn't exist
        companyService.getCompanyById(companyId);

        // If we reach here, the company exists, so fetch reviews
        return reviewRepository.findByCompanyId(companyId);
    }


    public boolean addReview(Long companyId, Review review){
    Company company = companyService.getCompanyById(companyId); // throws exception if not found
    review.setCompany(company);
    reviewRepository.save(review);
    return true;
}

    public Review getReviewById(Long companyId, Long reviewId) {
        Company company = companyService.getCompanyById(companyId); // throws if not found

        return reviewRepository.findById(reviewId)
                .filter(review -> review.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new ReviewNotFoundException(
                        "Review with ID " + reviewId + " does not exist for the company with ID " + companyId + ". Please make sure both IDs are correct."
                ));
    }


    public void updateReview(Long companyId, Long reviewId, Review updatedReview) {
        // Check if the company exists
        Company company = companyService.getCompanyById(companyId); // throws exception if not found
        if (company == null) {
            throw new CompanyNotFoundException("Company with ID " + companyId + " does not exist.");
        }

        // Check if the review exists and belongs to the given company
        Review existingReview = reviewRepository.findById(reviewId)
                .filter(r -> r.getCompany().getId().equals(companyId))
                .orElseThrow(() -> new ReviewNotFoundException(
                        "Review with ID " + reviewId + " does not exist for the company with ID " + companyId + "."
                ));

        // Update the review fields
        existingReview.setTitle(updatedReview.getTitle());
        existingReview.setDescription(updatedReview.getDescription());
        existingReview.setRating(updatedReview.getRating());

        // Save the updated review
        reviewRepository.save(existingReview);
    }


    public boolean deleteReview(Long companyId, Long reviewId) {
        // Check if company exists
        Company company = companyService.getCompanyById(companyId);
        if (company == null) {
            throw new CompanyNotFoundException("Company with ID " + companyId + " does not exist.");
        }

        // Check if review exists and belongs to the company
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();
            if (review.getCompany().getId().equals(companyId)) {
                reviewRepository.delete(review);
                return true;
            } else {
                throw new ReviewNotFoundException("Review with ID " + reviewId + " does not belong to company ID " + companyId + ".");
            }
        } else {
            throw new ReviewNotFoundException("Review with ID " + reviewId + " does not exist.");
        }
    }



}
