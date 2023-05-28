package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class UserRepositoryImpl implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  @Autowired
  public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    Assert.notNull(username, "Username cannot be null");
    return jpaUserRepository.findByUsername(username);
  }

  @Override
  public Boolean existsByUsernameOrEmail(String username, String email) {
    Assert.notNull(username, "Username cannot be null");
    Assert.notNull(email, "Email cannot be null");
    return jpaUserRepository.existsByUsernameOrEmail(username, email);
  }

  @Override
  public UserEntity save(UserEntity entity) {
    Assert.notNull(entity, "Entity cannot be null");
    return jpaUserRepository.save(entity);
  }

  @Override
  public Optional<UserEntity> findByClientCode(Integer clientCode) {
    Assert.notNull(clientCode, "Client code cannot be null");
    return jpaUserRepository.findByClientCode(clientCode);
  }

  @Override
  public List<UserEntity> findAllCustomers() {
    return jpaUserRepository.findAllCustomers();
  }
}
