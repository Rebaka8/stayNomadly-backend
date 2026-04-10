package com.staynomadly.api.repository;

import com.staynomadly.api.entity.HomestayListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HomestayListingRepository extends JpaRepository<HomestayListing, Long> {
    List<HomestayListing> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<HomestayListing> findByHostId(Long hostId);
}
