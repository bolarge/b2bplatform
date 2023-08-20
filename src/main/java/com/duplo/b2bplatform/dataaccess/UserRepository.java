package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
