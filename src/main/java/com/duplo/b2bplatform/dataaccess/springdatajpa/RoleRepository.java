package com.duplo.b2bplatform.dataaccess.springdatajpa;

import com.duplo.b2bplatform.domain.onboarding.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
