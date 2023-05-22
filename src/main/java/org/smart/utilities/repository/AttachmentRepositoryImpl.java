package org.smart.utilities.repository;

import java.util.Optional;
import org.smart.utilities.entity.AttachmentEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

  private final JpaAttachmentRepository jpaAttachmentRepository;

  public AttachmentRepositoryImpl(JpaAttachmentRepository jpaAttachmentRepository) {
    this.jpaAttachmentRepository = jpaAttachmentRepository;
  }

  @Override
  public Optional<AttachmentEntity> findByIdAndExpenseId(Integer attachmentId, Integer expenseId) {
    Assert.notNull(attachmentId, "Attachment id cannot be null");
    return jpaAttachmentRepository.findByIdAndExpenseId(attachmentId, expenseId);
  }
}
