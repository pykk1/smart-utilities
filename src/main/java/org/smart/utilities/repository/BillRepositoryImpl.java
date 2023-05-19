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
public class BillRepositoryImpl implements BillRepository{

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
  public List<BillEntity> getAllBills(Boolean paid) {
    Assert.notNull(paid, "Paid cannot be null");
    return jpaBillRepository.findByPaid(paid);
  }

  @Override
  public List<BillEntity> getBillsPerTypeAndPaid(BillType billType, Boolean paid, UserEntity user) {
    Assert.notNull(billType, "BillType cannot be null");
    Assert.notNull(user, "User cannot be null");
    return jpaBillRepository.findByBillTypeAndUserAndPaid(billType, user, paid);
  }

  @Override
  public Optional<BillEntity> getLastBill(BillType billType, UserEntity user) {
    Assert.notNull(billType, "BillType cannot be null");
    Assert.notNull(user, "User cannot be null");
    return jpaBillRepository.findFirstByBillTypeAndUserOrderByToDateDesc(billType, user);
  }

  @Override
  public List<BillEntity> findAllBills(Boolean paid, UserEntity user) {
    Assert.notNull(paid, "Paid cannot be null");
    Assert.notNull(user, "User cannot be null");
    return jpaBillRepository.findByPaidAndUserOrderByDueDateAsc(paid, user);
  }
}
