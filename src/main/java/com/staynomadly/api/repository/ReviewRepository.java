package com.staynomadly.api.repository;

import com.staynomadly.api.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByHomestayListingId(Long listingId);
    List<Review> findByHomestayListingIdAndCategory(Long listingId, String category);
}
