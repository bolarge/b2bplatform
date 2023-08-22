package com.duplo.b2bplatform.domain.onboarding;

import com.duplo.b2bplatform.domain.BaseEntity;
import com.duplo.b2bplatform.domain.order.PurchaseOrder;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "departments")
@Entity
public class Department extends BaseEntity {
    private String departmentName;
    private String departmentEmail;
    private String departmentDescription;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Business business;
    @OneToOne
    private User departmentHead;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> departmentStaff = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseOrder> departmentPurchaseOrders = new ArrayList<>();

    public Department(String departmentName, String departmentEmail, String departmentDescription) {
        this.departmentName = departmentName;
        this.departmentEmail = departmentEmail;
        this.departmentDescription = departmentDescription;
    }

    public void addUserToDepartment(User user) {
        user.setDepartment(this);
        getDepartmentStaff().add(user);
    }
    public void removeUserFromDepartment(User user) {
        getDepartmentStaff().remove(user);
    }

    public void addUsersToDepartment(Set<User> users) {
        ((User) users).setDepartment(this);
        getDepartmentStaff().addAll(users);
    }

    public void addDepartmentOrder(PurchaseOrder purchaseOrder) {
        purchaseOrder.setDepartment(this);
        getDepartmentPurchaseOrders().add(purchaseOrder);
    }
    public void removeDepartmentOrder(PurchaseOrder purchaseOrder) {
        getDepartmentPurchaseOrders().remove(purchaseOrder);
    }

    public void addDepartmentOrders(Set<PurchaseOrder> purchaseOrders) {
        ((PurchaseOrder) purchaseOrders).setDepartment(this);
        getDepartmentPurchaseOrders().addAll(purchaseOrders);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getDepartmentName(), that.getDepartmentName()) && Objects.equals(getBusiness(), that.getBusiness());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDepartmentName(), getBusiness());
    }
}
