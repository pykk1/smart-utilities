package org.smart.utilities.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;

public interface BillRepository {

  BillEntity save(BillEntity entity);

  List<BillEntity> getAllBills(Boolean paid);

  List<BillEntity> getBillsPerTypeAndPaid(BillType billType, Boolean paid, UserEntity user);

  Optional<BillEntity> getLastBill(BillType billType, UserEntity user);

  List<BillEntity> findAllBills(Boolean paid, UserEntity user);
}