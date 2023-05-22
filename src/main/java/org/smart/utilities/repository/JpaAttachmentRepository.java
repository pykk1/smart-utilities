package org.smart.utilities.repository;

import java.util.Optional;
import org.smart.utilities.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAttachmentRepository extends JpaRepository<AttachmentEntity, Integer> {

  Optional<AttachmentEntity> findByIdAndExpenseId(Integer id, Integer expenseId);
}
