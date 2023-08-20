package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Integer> {
}
