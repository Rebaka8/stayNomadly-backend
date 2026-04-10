package com.staynomadly.api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_id", nullable = false)
    private User guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id", nullable = false)
    private TouristAttraction attraction;

    @Column(length = 500)
    private String reason;
}
