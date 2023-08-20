package com.duplo.b2bplatform.dataaccess;

import com.duplo.b2bplatform.domain.onboarding.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
