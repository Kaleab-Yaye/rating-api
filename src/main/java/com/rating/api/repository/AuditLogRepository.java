package com.rating.api.repository;

import com.rating.api.entity.AuditLog;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

  @Query("SELECT al FROM AuditLog al WHERE al.userId = :userId")
  List<AuditLog> findByUserId(@Param("userId") UUID userId);

  @Query("SELECT al FROM AuditLog al WHERE al.medicine.id = :medicineId")
  List<AuditLog> findByMedicineId(@Param("medicineId") Long medicineId);

  @Query("SELECT al FROM AuditLog al WHERE al.batch.id = :batchId")
  List<AuditLog> findByBatchId(@Param("batchId") Long batchId);

  @Query("SELECT al FROM AuditLog al WHERE al.order.id = :orderId")
  List<AuditLog> findByOrderId(@Param("orderId") Long orderId);

  @Query("SELECT al FROM AuditLog al WHERE al.action = :action")
  List<AuditLog> findByAction(@Param("action") String action);

  @Query("SELECT al FROM AuditLog al WHERE al.userType = :userType")
  List<AuditLog> findByUserType(@Param("userType") String userType);

  @Query("SELECT al FROM AuditLog al WHERE al.medicine.id = :medicineId AND al.batch.id = :batchId")
  List<AuditLog> findByMedicineAndBatch(
      @Param("medicineId") Long medicineId, @Param("batchId") Long batchId);
}
