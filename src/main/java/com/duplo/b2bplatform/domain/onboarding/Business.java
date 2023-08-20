package com.duplo.b2bplatform.domain.onboarding;

import com.duplo.b2bplatform.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "businesses")
@Entity
public class Business extends BaseEntity {
    private UUID businessId;
    private String businessName;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<User> theStaff = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "business", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Department> theDepartments = new HashSet<>();

    public void addDepartmentToBusiness(Department department) {
        department.setBusiness(this);
        getTheDepartments().add(department);
    }
    public void removeDepartmentFromBusiness(Department department) {
        getTheDepartments().remove(department);
    }

    public void addDepartmentsToBusiness(Set<Department> departments) {
        ((Department) departments).setBusiness(this);
        getTheDepartments().addAll(departments);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Business business = (Business) o;
        return Objects.equals(getBusinessId(), business.getBusinessId()) && Objects.equals(getBusinessName(), business.getBusinessName()) && Objects.equals(getEmail(), business.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBusinessId(), getBusinessName(), getEmail());
    }
}
