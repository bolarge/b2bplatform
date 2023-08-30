package com.duplo.b2bplatform.dataaccess.springdatajpa;

import com.duplo.b2bplatform.dataaccess.BaseRepository;
import com.duplo.b2bplatform.domain.onboarding.User;
import org.springframework.context.annotation.Profile;

//public interface UserRepository extends JpaRepository<User, Integer> {
@Profile("spring-data-jpa")
public interface UserRepository extends BaseRepository<User> {
}
