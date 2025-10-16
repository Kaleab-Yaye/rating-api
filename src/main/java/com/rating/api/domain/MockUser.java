package com.rating.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "first_mock_users")
public class MockUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long Id;

  @Version Integer version;

  String Name;
  Integer rated;
}
