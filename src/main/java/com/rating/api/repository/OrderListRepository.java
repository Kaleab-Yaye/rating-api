package com.rating.api.repository;

import com.rating.api.entity.OrderList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Long> {

  @Query("SELECT ol FROM OrderList ol WHERE ol.order.id = :orderId")
  List<OrderList> findByOrderId(@Param("orderId") Long orderId);

  @Query("SELECT ol FROM OrderList ol WHERE ol.medicine.id = :medicineId")
  List<OrderList> findByMedicineId(@Param("medicineId") Long medicineId);

  @Query(
      "SELECT ol FROM OrderList ol WHERE ol.order.id = :orderId AND ol.medicine.id = :medicineId")
  Optional<OrderList> findByOrderIdAndMedicineId(
      @Param("orderId") Long orderId, @Param("medicineId") Long medicineId);
}
