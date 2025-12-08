package com.rating.api.domain;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long id;

    @Column(name = "user_id")
    private @Getter @Setter UUID userId;

    @Column(name = "user_type")
    private @Getter @Setter String userType;

    @ManyToOne
    @JoinColumn(name = "med_id")
    private @Getter @Setter Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private @Getter @Setter MedBatch batch;

    private @Getter @Setter String action;

    @Column(name = "batch_change_details", columnDefinition = "JSONB")
    private @Getter @Setter String batchChangeDetails;

    @Column(name = "initial_batch_state")
    private @Getter @Setter Long initialBatchState;

    @Column(name = "current_batch_state")
    private @Getter @Setter Long currentBatchState;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private @Getter @Setter Order order;

    @Version
    private @Getter @Setter Integer version;
}
