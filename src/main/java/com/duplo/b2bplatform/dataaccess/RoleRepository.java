package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
