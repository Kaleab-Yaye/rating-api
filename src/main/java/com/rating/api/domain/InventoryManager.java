package com.rating.api.domain;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_managers")
public class InventoryManager {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private @Getter @Setter UUID id;

    @Column(nullable = false, unique = true)
    private @Getter @Setter String name;

    @Column(nullable = false, unique = true)
    private @Getter @Setter String email;

    @Column(name = "is_admin")
    private @Getter @Setter Boolean isAdmin;

    @Version
    private @Getter @Setter Integer version;
}
