package com.cyberlearn.osahaneat.repository;

import com.cyberlearn.osahaneat.entity.OrderItem;
import com.cyberlearn.osahaneat.entity.keys.KeyOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, KeyOrderItem> {
}
