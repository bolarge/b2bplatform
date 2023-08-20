package com.duplo.b2bplatform.domain.order;

import com.duplo.b2bplatform.domain.NamedEntity;
import com.duplo.b2bplatform.domain.onboarding.Department;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "orders")
@Entity
public class Order extends NamedEntity {
    private UUID purchaseRequest;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
}
