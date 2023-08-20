package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
