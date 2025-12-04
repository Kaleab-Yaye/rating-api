package com.rating.api.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "inventory_managers")
public class InventoryManager {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(name = "name", unique = true, nullable = false, length = 255)
  private String name;

  @Column(name = "email", unique = true, nullable = false, length = 255)
  private String email;

  @Column(name = "is_admin")
  private Boolean isAdmin = false;

  @Version private Long version;

  public InventoryManager() {}

  public InventoryManager(String name, String email, Boolean isAdmin) {
    this.name = name;
    this.email = email;
    this.isAdmin = isAdmin;
  }

  // Getters and setters
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
