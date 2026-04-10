package com.staynomadly.api.controller;

import com.staynomadly.api.entity.HomestayListing;
import com.staynomadly.api.repository.HomestayListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class HomestayListingController {

    private final HomestayListingRepository listingRepository;

    @GetMapping
    public ResponseEntity<List<HomestayListing>> getAllListings(
            @RequestParam(required = false, defaultValue = "0") BigDecimal minPrice,
            @RequestParam(required = false, defaultValue = "999999") BigDecimal maxPrice) {
        return ResponseEntity.ok(listingRepository.findByPricePerNightBetween(minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HomestayListing> getListingById(@PathVariable Long id) {
        return listingRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<HomestayListing>> getListingsByHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(listingRepository.findByHostId(hostId));
    }

    @PostMapping
    public ResponseEntity<HomestayListing> createListing(@RequestBody HomestayListing listing) {
        return ResponseEntity.ok(listingRepository.save(listing));
    }
}
