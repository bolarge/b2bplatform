package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.Department;
import com.duplo.b2bplatform.domain.order.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    List<PurchaseOrder> findPurchaseOrderByDepartment(Department department);
}
