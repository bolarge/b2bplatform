package com.duplo.b2bplatform.domain.inventory;

import com.duplo.b2bplatform.domain.NamedEntity;
import com.duplo.b2bplatform.domain.order.PurchaseOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "inventories")
@Entity
public class Inventory extends NamedEntity {
    @OneToOne
    private PurchaseOrder purchaseOrder;
    private int quantity;
}
