package com.staynomadly.api.repository;

import com.staynomadly.api.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByHomestayListingHostIdOrderByCheckInDateDesc(Long hostId);
    List<Booking> findByTouristIdOrderByCheckInDateDesc(Long touristId);
}
