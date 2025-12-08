package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @ManyToOne
    @JoinColumn(name = "pharmacist_id", nullable = false)
    private @Getter @Setter Pharmacist pharmacist;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private @Getter @Setter Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medicines_id", nullable = false)
    private @Getter @Setter Medicine medicine;

    @Column(nullable = false)
    private @Getter @Setter Integer rating;
}
