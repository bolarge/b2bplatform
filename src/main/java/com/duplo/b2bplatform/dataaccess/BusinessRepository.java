package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
    Optional<Business> findBusinessByBusinessId(UUID businessId);
}
