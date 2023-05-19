package org.smart.utilities.repository;

import java.util.List;
import java.util.Optional;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBillRepository extends JpaRepository<BillEntity, Integer> {

  List<BillEntity> findByPaid(Boolean paid);

  List<BillEntity> findByBillTypeAndUserAndPaid(BillType billType, UserEntity user, Boolean paid);

//  List<BillEntity> findByBillTypeAndUser(BillType billType, UserEntity user);

  Optional<BillEntity> findFirstByBillTypeAndUserOrderByToDateDesc(BillType billType,
      UserEntity user);

  List<BillEntity> findByPaidAndUser(Boolean paid, UserEntity user);
}
