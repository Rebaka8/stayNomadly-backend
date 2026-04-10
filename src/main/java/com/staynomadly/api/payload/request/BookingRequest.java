package com.staynomadly.api.payload.request;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long touristId;
    private Long listingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalPrice;
}
