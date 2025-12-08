package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medicines")
public class Medicine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @Column(nullable = false, unique = true)
  private @Getter @Setter String name;

  @Column(columnDefinition = "TEXT")
  private @Getter @Setter String about;

  private @Getter @Setter Long price;

  @Column(name = "average_rating")
  private @Getter @Setter Integer averageRating;

  @Version private @Getter @Setter Integer version;
}
