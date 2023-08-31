package com.duplo.b2bplatform.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.duplo.b2bplatform.domain.onboarding.User;

@NoRepositoryBean
public interface BaseRepository<T extends User> extends JpaRepository<T, Integer> {

}
