package com.rating.api.service;

import com.rating.api.entity.*;
import com.rating.api.repository.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
  private final OrderRepository orderRepository;
  private final OrderListRepository orderListRepository;
  private final PharmacistRepository pharmacistRepository;
  private final PharmacyRepository pharmacyRepository;
  private final MedicineRepository medicineRepository;
  private final MedicineService medicineService;

  public OrderService(
      OrderRepository orderRepository,
      OrderListRepository orderListRepository,
      PharmacistRepository pharmacistRepository,
      PharmacyRepository pharmacyRepository,
      MedicineRepository medicineRepository,
      MedicineService medicineService) {
    this.orderRepository = orderRepository;
    this.orderListRepository = orderListRepository;
    this.pharmacistRepository = pharmacistRepository;
    this.pharmacyRepository = pharmacyRepository;
    this.medicineRepository = medicineRepository;
    this.medicineService = medicineService;
  }

  public Order createOrder(Order order) {
    Pharmacist pharmacist =
        pharmacistRepository
            .findById(order.getPharmacist().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacist not found"));
    Pharmacy pharmacy =
        pharmacyRepository
            .findById(order.getPharmacy().getId())
            .orElseThrow(() -> new RuntimeException("Pharmacy not found"));
    order.setPharmacist(pharmacist);
    order.setPharmacy(pharmacy);
    return orderRepository.save(order);
  }

  public OrderList addItemToOrder(Long orderId, Long medicineId, Integer quantity) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    Medicine medicine =
        medicineRepository
            .findById(medicineId)
            .orElseThrow(() -> new RuntimeException("Medicine not found"));

    // Check stock availability
    if (!medicineService.isStockAvailable(medicineId, quantity)) {
      throw new RuntimeException("Insufficient stock for medicine: " + medicine.getName());
    }

    // Check if item already exists in order
    Optional<OrderList> existingItem =
        orderListRepository.findByOrderIdAndMedicineId(orderId, medicineId);
    if (existingItem.isPresent()) {
      OrderList item = existingItem.get();
      item.setAmountOrdered(item.getAmountOrdered() + quantity);
      return orderListRepository.save(item);
    } else {
      OrderList newItem = new OrderList(medicine, order, quantity);
      return orderListRepository.save(newItem);
    }
  }

  public Optional<Order> getOrderById(Long id) {
    return orderRepository.findById(id);
  }

  public List<Order> getOrdersByPharmacyId(Long pharmacyId) {
    return orderRepository.findByPharmacyId(pharmacyId);
  }

  public List<Order> getOrdersByPharmacistId(UUID pharmacistId) {
    return orderRepository.findByPharmacistId(pharmacistId);
  }

  public List<Order> getOpenOrders() {
    return orderRepository.findOpenOrders();
  }

  public List<Order> getDeliveredOrders() {
    return orderRepository.findDeliveredOrders();
  }

  public List<OrderList> getOrderItems(Long orderId) {
    return orderListRepository.findByOrderId(orderId);
  }

  public Order markOrderAsDelivered(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setOrderDeliveredDate(LocalDateTime.now());
    order.setOrderClosed(true);
    return orderRepository.save(order);
  }

  public Order closeOrder(Long orderId) {
    Order order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    order.setOrderClosed(true);
    return orderRepository.save(order);
  }

  public void removeItemFromOrder(Long orderItemId) {
    OrderList orderItem =
        orderListRepository
            .findById(orderItemId)
            .orElseThrow(() -> new RuntimeException("Order item not found"));
    orderListRepository.delete(orderItem);
  }

  public void deleteOrder(Long id) {
    Order order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    orderRepository.delete(order);
  }
}
