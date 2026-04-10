package com.staynomadly.api.controller;

import com.staynomadly.api.entity.Review;
import com.staynomadly.api.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<Review>> getReviewsByListing(
            @PathVariable Long listingId,
            @RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty()) {
            return ResponseEntity.ok(reviewRepository.findByHomestayListingIdAndCategory(listingId, category));
        }
        return ResponseEntity.ok(reviewRepository.findByHomestayListingId(listingId));
    }
}
