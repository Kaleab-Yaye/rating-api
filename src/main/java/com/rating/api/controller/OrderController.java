package com.rating.api.controller;

import com.rating.api.entity.Order;
import com.rating.api.entity.OrderList;
import com.rating.api.service.OrderService;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody Order order) {
    try {
      Order createdOrder = orderService.createOrder(order);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @PostMapping("/{orderId}/items")
  public ResponseEntity<?> addItemToOrder(
      @PathVariable Long orderId, @RequestBody Map<String, Object> itemRequest) {
    try {
      Long medicineId = Long.valueOf(itemRequest.get("medicineId").toString());
      Integer quantity = Integer.valueOf(itemRequest.get("quantity").toString());
      OrderList orderItem = orderService.addItemToOrder(orderId, medicineId, quantity);
      return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOrderById(@PathVariable Long id) {
    return orderService
        .getOrderById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/pharmacy/{pharmacyId}")
  public ResponseEntity<List<Order>> getOrdersByPharmacy(@PathVariable Long pharmacyId) {
    List<Order> orders = orderService.getOrdersByPharmacyId(pharmacyId);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/pharmacist/{pharmacistId}")
  public ResponseEntity<List<Order>> getOrdersByPharmacist(@PathVariable UUID pharmacistId) {
    List<Order> orders = orderService.getOrdersByPharmacistId(pharmacistId);
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/open")
  public ResponseEntity<List<Order>> getOpenOrders() {
    List<Order> orders = orderService.getOpenOrders();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/delivered")
  public ResponseEntity<List<Order>> getDeliveredOrders() {
    List<Order> orders = orderService.getDeliveredOrders();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{orderId}/items")
  public ResponseEntity<List<OrderList>> getOrderItems(@PathVariable Long orderId) {
    List<OrderList> orderItems = orderService.getOrderItems(orderId);
    return ResponseEntity.ok(orderItems);
  }

  @PatchMapping("/{orderId}/deliver")
  public ResponseEntity<?> markOrderAsDelivered(@PathVariable Long orderId) {
    try {
      Order updatedOrder = orderService.markOrderAsDelivered(orderId);
      return ResponseEntity.ok(updatedOrder);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PatchMapping("/{orderId}/close")
  public ResponseEntity<?> closeOrder(@PathVariable Long orderId) {
    try {
      Order updatedOrder = orderService.closeOrder(orderId);
      return ResponseEntity.ok(updatedOrder);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/items/{orderItemId}")
  public ResponseEntity<Void> removeItemFromOrder(@PathVariable Long orderItemId) {
    try {
      orderService.removeItemFromOrder(orderItemId);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    try {
      orderService.deleteOrder(id);
      return ResponseEntity.noContent().build();
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
