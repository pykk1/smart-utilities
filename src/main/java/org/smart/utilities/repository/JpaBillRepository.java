package org.smart.utilities.repository;

import java.util.List;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBillRepository extends JpaRepository<BillEntity, Integer> {

  List<BillEntity> findByPaidOrderByDueDateAsc(Boolean paid);

  List<BillEntity> findByPaidOrderByIssueDateDesc(Boolean paid);

  List<BillEntity> findByBillTypeAndUserAndPaidOrderByDueDateAsc(BillType billType, UserEntity user, Boolean paid);

  List<BillEntity> findByBillTypeAndUserAndPaidOrderByIssueDateDesc(BillType billType, UserEntity user, Boolean paid);

  List<BillEntity> findByPaidAndUserOrderByDueDateAsc(Boolean paid, UserEntity user);

  List<BillEntity> findByPaidAndUserOrderByIssueDateDesc(Boolean paid, UserEntity user);
}
