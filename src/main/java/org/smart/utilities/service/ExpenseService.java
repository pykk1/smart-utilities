package org.smart.utilities.service;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.entity.AttachmentEntity;
import org.smart.utilities.entity.ExpenseEntity;
import org.smart.utilities.entity.converters.Converter;
import org.smart.utilities.repository.AttachmentRepository;
import org.smart.utilities.repository.ExpenseRepository;
import org.smart.utilities.repository.UserRepository;
import org.smart.utilities.security.JWTGenerator;
import org.smart.utilities.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExpenseService {

  private final Validator<ExpenseDTO> validator;
  private final ExpenseRepository expenseRepository;
  private final Converter<ExpenseDTO, ExpenseEntity> converter;
  private final UserRepository userRepository;
  private final JWTGenerator jwtGenerator;
  private final AttachmentRepository attachmentRepository;

  @Autowired
  public ExpenseService(Validator<ExpenseDTO> validator, ExpenseRepository expenseRepository,
      Converter<ExpenseDTO, ExpenseEntity> converter, UserRepository userRepository,
      JWTGenerator jwtGenerator, AttachmentRepository attachmentRepository) {
    this.validator = validator;
    this.expenseRepository = expenseRepository;
    this.converter = converter;
    this.userRepository = userRepository;
    this.jwtGenerator = jwtGenerator;
    this.attachmentRepository = attachmentRepository;
  }

  public ExpenseDTO createExpense(ExpenseDTO expense, List<MultipartFile> attachments,
      HttpServletRequest request) throws Exception {
    validator.validate(expense);
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    var entity = converter.convertToEntity(expense);
    entity.setUser(userEntity);
    entity.setDate(expense.getDate().atOffset(ZoneOffset.UTC).with(LocalTime.MAX)
        .truncatedTo(ChronoUnit.SECONDS).toInstant());

    if (attachments != null) {
      for (MultipartFile file : attachments) {
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachment.setData(file.getBytes());
        attachment.setExpense(entity);
        entity.getAttachments().add(attachment);
      }
    }
    return converter.convertToDTO(expenseRepository.save(entity));
  }

  @Transactional
  public AttachmentEntity downloadAttachment(Integer expenseId, Integer attachmentId)
      throws NotFoundException {
    return attachmentRepository.findByIdAndExpenseId(attachmentId, expenseId)
        .orElseThrow(NotFoundException::new);
  }

  @Transactional
  public List<ExpenseDTO> getExpenses(HttpServletRequest request, Boolean paid) throws Exception {
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    return expenseRepository.getAll(paid, userEntity)
        .stream()
        .map(converter::convertToDTO)
        .collect(Collectors.toList());
  }

  public void payExpense(Integer expenseId) throws NotFoundException {
    var expenseEntity = expenseRepository.findById(expenseId).orElseThrow(NotFoundException::new);
    expenseEntity.setPaid(true);
    expenseRepository.save(expenseEntity);
  }
}
