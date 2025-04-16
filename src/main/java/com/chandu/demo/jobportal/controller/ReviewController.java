package com.chandu.demo.jobportal.controller;

import com.chandu.demo.jobportal.customException.CompanyNotFoundException;
import com.chandu.demo.jobportal.customException.ReviewNotFoundException;
import com.chandu.demo.jobportal.model.Review;
import com.chandu.demo.jobportal.service.CompanyService;
import com.chandu.demo.jobportal.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping("/reviews")
    public ResponseEntity<String> addReview(@PathVariable Long companyId, @RequestBody Review review){
        reviewService.addReview(companyId, review);
        return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long companyId,
                                            @PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(companyId, reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId,
                                               @RequestBody Review review) {
        try {
            // Call the service method to update the review
            reviewService.updateReview(companyId, reviewId, review);
            return new ResponseEntity<>("Review Updated Successfully", HttpStatus.OK);
        } catch (CompanyNotFoundException e) {
            // Handle company not found exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ReviewNotFoundException e) {
            // Handle review not found exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other potential exceptions
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long companyId,
                                               @PathVariable Long reviewId) {
        try {
            boolean isReviewDeleted = reviewService.deleteReview(companyId, reviewId);
            if (isReviewDeleted) {
                return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Review could not be deleted", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (CompanyNotFoundException | ReviewNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
