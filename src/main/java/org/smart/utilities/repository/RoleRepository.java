package org.smart.utilities.repository;

import java.util.Optional;
import org.smart.utilities.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

  Optional<RoleEntity> findByName(String name);
}
