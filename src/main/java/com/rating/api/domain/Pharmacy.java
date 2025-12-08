package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column(nullable = false, unique = true)
    private @Getter @Setter String name;

    @Column(name = "street_address", nullable = false)
    private @Getter @Setter String streetAddress;

    @Column(nullable = false)
    private @Getter @Setter String city;

    @Column(nullable = false)
    private @Getter @Setter String region;

    @Column(name = "postal_code")
    private @Getter @Setter String postalCode;

    private @Getter @Setter Long balance;

    @Version
    private @Getter @Setter Integer version;
}
