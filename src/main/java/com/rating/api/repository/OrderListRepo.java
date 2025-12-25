package com.rating.api.repository;

import com.rating.api.domain.OrderList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderListRepo extends JpaRepository<OrderList, Long> {}
