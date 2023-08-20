package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}
