package org.smart.utilities.service;

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.smart.utilities.dto.AttachmentDTO;
import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.entity.AttachmentEntity;
import org.smart.utilities.entity.ExpenseEntity;
import org.smart.utilities.entity.converters.Converter;
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
  private final Converter<AttachmentDTO, AttachmentEntity> attachmentConverter;

  @Autowired
  public ExpenseService(Validator<ExpenseDTO> validator, ExpenseRepository expenseRepository,
      Converter<ExpenseDTO, ExpenseEntity> converter, UserRepository userRepository,
      JWTGenerator jwtGenerator, Converter<AttachmentDTO, AttachmentEntity> attachmentConverter) {
    this.validator = validator;
    this.expenseRepository = expenseRepository;
    this.converter = converter;
    this.userRepository = userRepository;
    this.jwtGenerator = jwtGenerator;
    this.attachmentConverter = attachmentConverter;
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
  public AttachmentEntity getExpenseAttachment(Integer expenseId, Integer attachmentId) throws NotFoundException {
    ExpenseEntity expenseEntity = expenseRepository.findById(expenseId).orElseThrow(NotFoundException::new);
    return expenseEntity.getAttachments().stream()
        .filter(attachment -> attachment.getId().equals(attachmentId))
        .findFirst()
        .orElseThrow(NotFoundException::new);
  }

  public List<ExpenseDTO> getExpenses(HttpServletRequest request, Boolean paid) throws Exception {
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    return expenseRepository.getAll(paid, userEntity)
        .stream()
        .map(converter::convertToDTO)
        .collect(Collectors.toList());
  }

}
