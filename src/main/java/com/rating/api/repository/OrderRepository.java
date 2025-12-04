package com.rating.api.repository;

import com.rating.api.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT o FROM Order o WHERE o.pharmacy.id = :pharmacyId")
  List<Order> findByPharmacyId(@Param("pharmacyId") Long pharmacyId);

  @Query("SELECT o FROM Order o WHERE o.pharmacist.id = :pharmacistId")
  List<Order> findByPharmacistId(@Param("pharmacistId") UUID pharmacistId);

  @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
  List<Order> findOrdersBetweenDates(
      @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

  @Query("SELECT o FROM Order o WHERE o.orderClosed = false")
  List<Order> findOpenOrders();

  @Query("SELECT o FROM Order o WHERE o.orderClosed = true AND o.orderDeliveredDate IS NOT NULL")
  List<Order> findDeliveredOrders();
}
