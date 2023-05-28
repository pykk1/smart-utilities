package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;

public interface BillRepository {

  BillEntity save(BillEntity entity);

  List<BillEntity> getAll(Boolean paid);

  List<BillEntity> getByTypeAndPaid(BillType billType, Boolean paid, UserEntity user);

  List<BillEntity> getAll(Boolean paid, UserEntity user);

  Optional<BillEntity> getById(Integer billId);
}