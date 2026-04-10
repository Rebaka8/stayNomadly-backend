package com.staynomadly.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourist_id", nullable = false)
    private User tourist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private HomestayListing homestayListing;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 500)
    private String comment;

    @Column(length = 500)
    private String hostReply;

    @Column(length = 50)
    private String category;
}
