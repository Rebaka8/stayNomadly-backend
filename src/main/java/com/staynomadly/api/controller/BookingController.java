package com.staynomadly.api.controller;

import com.staynomadly.api.entity.Booking;
import com.staynomadly.api.entity.HomestayListing;
import com.staynomadly.api.entity.User;
import com.staynomadly.api.enums.BookingStatus;
import com.staynomadly.api.payload.request.BookingRequest;
import com.staynomadly.api.repository.BookingRepository;
import com.staynomadly.api.repository.HomestayListingRepository;
import com.staynomadly.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final HomestayListingRepository listingRepository;

    @GetMapping("/tourist/{touristId}")
    public ResponseEntity<List<Booking>> getBookingsForTourist(@PathVariable Long touristId) {
        return ResponseEntity.ok(bookingRepository.findByTouristIdOrderByCheckInDateDesc(touristId));
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request) {
        User tourist = userRepository.findById(request.getTouristId()).orElse(null);
        HomestayListing listing = listingRepository.findById(request.getListingId()).orElse(null);

        if (tourist == null || listing == null) {
            return ResponseEntity.badRequest().body("User or Listing not found");
        }

        Booking booking = new Booking();
        booking.setTourist(tourist);
        booking.setHomestayListing(listing);
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setTotalPrice(request.getTotalPrice());
        booking.setStatus(BookingStatus.CONFIRMED); // Set to confirmed for this simple flow

        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<Booking>> getBookingsForHost(@PathVariable Long hostId) {
        return ResponseEntity.ok(bookingRepository.findByHomestayListingHostIdOrderByCheckInDateDesc(hostId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestParam BookingStatus status) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus(status);
            return ResponseEntity.ok(bookingRepository.save(booking));
        }).orElse(ResponseEntity.notFound().build());
    }
}
