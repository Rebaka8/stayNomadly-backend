package com.staynomadly.api.controller;

import com.staynomadly.api.entity.HomestayListing;
import com.staynomadly.api.entity.User;
import com.staynomadly.api.repository.HomestayListingRepository;
import com.staynomadly.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RestController
@RequestMapping("/api/users/{userId}/wishlist")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class WishlistController {

    private final UserRepository userRepository;
    private final HomestayListingRepository listingRepository;

    @GetMapping
    @Transactional
    public ResponseEntity<Set<HomestayListing>> getWishlist(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Set<HomestayListing> wishlists = user.getWishlistedProperties();
        wishlists.size(); // Force initialization
        return ResponseEntity.ok(wishlists);
    }

    @PostMapping("/{listingId}")
    public ResponseEntity<?> toggleWishlist(@PathVariable Long userId, @PathVariable Long listingId) {
        User user = userRepository.findById(userId).orElse(null);
        HomestayListing listing = listingRepository.findById(listingId).orElse(null);

        if (user == null || listing == null) {
            return ResponseEntity.notFound().build();
        }

        boolean added = false;
        if (user.getWishlistedProperties().contains(listing)) {
            user.getWishlistedProperties().remove(listing);
        } else {
            user.getWishlistedProperties().add(listing);
            added = true;
        }

        userRepository.save(user);
        return ResponseEntity.ok(added);
    }
}
