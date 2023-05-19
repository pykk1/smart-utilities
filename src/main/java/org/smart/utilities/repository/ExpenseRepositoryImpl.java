package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.entity.ExpenseEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

  private final JpaExpenseRepository jpaExpenseRepository;

  @Autowired
  public ExpenseRepositoryImpl(JpaExpenseRepository jpaExpenseRepository) {
    this.jpaExpenseRepository = jpaExpenseRepository;
  }

  @Override
  public ExpenseEntity save(ExpenseEntity entity) {
    Assert.notNull(entity, "Entity cannot be null");
    return jpaExpenseRepository.save(entity);
  }

  @Override
  public List<ExpenseEntity> getAll(Boolean paid, UserEntity user) {
    Assert.notNull(paid, "Paid cannot be null");
    Assert.notNull(user, "User cannot be null");
    return jpaExpenseRepository.findByPaidAndUserOrderByDateDesc(paid, user);
  }

  @Override
  public Optional<ExpenseEntity> findById(Integer id) {
    Assert.notNull(id, "Id cannot be null");
    return jpaExpenseRepository.findById(id);
  }
}
