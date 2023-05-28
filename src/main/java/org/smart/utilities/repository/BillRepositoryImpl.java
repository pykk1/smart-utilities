package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class BillRepositoryImpl implements BillRepository {

  private final JpaBillRepository jpaBillRepository;

  @Autowired
  public BillRepositoryImpl(JpaBillRepository jpaBillRepository) {
    this.jpaBillRepository = jpaBillRepository;
  }

  @Override
  public BillEntity save(BillEntity entity) {
    Assert.notNull(entity, "Entity cannot be null");
    return jpaBillRepository.save(entity);
  }

  @Override
  public List<BillEntity> getAll(Boolean paid) {
    Assert.notNull(paid, "Paid cannot be null");

    if (paid) {
      return jpaBillRepository.findByPaidOrderByIssueDateDesc(paid);
    }
    return jpaBillRepository.findByPaidOrderByDueDateAsc(paid);
  }

  @Override
  public List<BillEntity> getByTypeAndPaid(BillType billType, Boolean paid, UserEntity user) {
    Assert.notNull(billType, "BillType cannot be null");
    Assert.notNull(user, "User cannot be null");

    if (paid) {
      return jpaBillRepository.findByBillTypeAndUserAndPaidOrderByIssueDateDesc(billType, user,
          paid);
    }
    return jpaBillRepository.findByBillTypeAndUserAndPaidOrderByDueDateAsc(billType, user, paid);
  }

  @Override
  public List<BillEntity> getAll(Boolean paid, UserEntity user) {
    Assert.notNull(paid, "Paid cannot be null");
    Assert.notNull(user, "User cannot be null");

    if (paid) {
      return jpaBillRepository.findByPaidAndUserOrderByIssueDateDesc(paid, user);
    }
    return jpaBillRepository.findByPaidAndUserOrderByDueDateAsc(paid, user);
  }

  @Override
  public Optional<BillEntity> getById(Integer id) {
    Assert.notNull(id, "Id cannot be null");
    return jpaBillRepository.findById(id);
  }
}
