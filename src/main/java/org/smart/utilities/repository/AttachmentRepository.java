package org.smart.utilities.repository;

import java.util.Optional;
import org.smart.utilities.entity.AttachmentEntity;

public interface AttachmentRepository {

  Optional<AttachmentEntity> findByIdAndExpenseId(Integer attachmentId, Integer ExpenseId);
}