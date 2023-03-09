package org.smart.utilities.repository;

import java.util.Optional;
import org.smart.utilities.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

  Optional<UserEntity> findByUsername(String username);

  Boolean existsByUsername(String username);
}
