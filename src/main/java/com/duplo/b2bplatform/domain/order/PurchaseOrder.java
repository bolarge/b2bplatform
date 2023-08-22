package com.duplo.b2bplatform.domain.order;

import com.duplo.b2bplatform.domain.NamedEntity;
import com.duplo.b2bplatform.domain.onboarding.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
@Entity
public class PurchaseOrder extends NamedEntity {
    private UUID orderId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private BigDecimal itemUnitPrice;
    private BigDecimal totalPrice;
    private int orderQuantity;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    public PurchaseOrder(String itemName, String itemDescription, UUID purchaseRequest, OrderStatus orderStatus,
                         int orderQuantity, BigDecimal itemUnitPrice, BigDecimal totalPrice) {
        super(itemName, itemDescription);
        this.orderId = purchaseRequest;
        this.orderStatus = orderStatus;
        this.orderQuantity = orderQuantity;
        this.itemUnitPrice = itemUnitPrice;
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PurchaseOrder purchaseOrder = (PurchaseOrder) o;
        return Objects.equals(getId(), purchaseOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }
}
