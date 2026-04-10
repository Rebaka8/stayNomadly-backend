package com.staynomadly.api.entity;

import com.staynomadly.api.enums.ListingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "homestay_listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomestayListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User host;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String area;

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ListingStatus status = ListingStatus.DRAFT;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> photosUrls;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> amenities;

    private boolean available = true;

    // Additional fields for StayNomadly UI Data mapping
    private Double rating;
    private String distance;
    private Boolean isGuestFavorite;
    private String category;
}
