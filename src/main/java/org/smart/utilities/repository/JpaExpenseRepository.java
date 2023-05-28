package org.smart.utilities.repository;

import java.util.List;
import org.smart.utilities.entity.ExpenseEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaExpenseRepository extends JpaRepository<ExpenseEntity, Integer> {

  List<ExpenseEntity> findByPaidAndUserOrderByDateDesc(Boolean paid, UserEntity user);

  List<ExpenseEntity> findByPaidOrderByDateDesc(Boolean paid);
}
