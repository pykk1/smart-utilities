package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.entity.ExpenseEntity;
import org.smart.utilities.entity.UserEntity;

public interface ExpenseRepository {

  ExpenseEntity save(ExpenseEntity entity);

  List<ExpenseEntity> getAll(Boolean paid, UserEntity user);

  Optional<ExpenseEntity> findById(Integer id);
}