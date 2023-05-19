package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.entity.RoleEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsernameOrEmail(String username, String email);

  Optional<UserEntity> findByClientCode(Integer clientCode);

  @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r.name = 'USER' OR r.name = 'ADMIN'")
  List<UserEntity> findAllCustomers();
}
