package org.smart.utilities.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.smart.utilities.dto.BillDTO;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.BillEntity;
import org.smart.utilities.entity.UserEntity;
import org.smart.utilities.entity.converters.Converter;
import org.smart.utilities.repository.BillRepository;
import org.smart.utilities.repository.UserRepository;
import org.smart.utilities.security.JWTGenerator;
import org.smart.utilities.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

  private final Validator<BillDTO> validator;
  private final BillRepository billRepository;
  private final Converter<BillDTO, BillEntity> converter;
  private final UserRepository userRepository;
  private final JWTGenerator jwtGenerator;

  @Autowired
  public BillService(Validator<BillDTO> validator, BillRepository billRepository,
      Converter<BillDTO, BillEntity> converter, UserRepository userRepository,
      JWTGenerator jwtGenerator) {
    this.validator = validator;
    this.billRepository = billRepository;
    this.converter = converter;
    this.userRepository = userRepository;
    this.jwtGenerator = jwtGenerator;
  }

  public BillDTO createAdminBill(BillDTO bill) {
    validator.validate(bill);

    var entity = converter.convertToEntity(bill);

    var user = userRepository.findByClientCode(bill.getClientCode())
        .orElseThrow(IllegalArgumentException::new);
    entity.setUser(user);

    var now = LocalDateTime.now().toInstant(ZoneOffset.UTC);

    entity.setIssueDate(now);
    setDates(bill, entity);
    entity.setPaid(false);
    entity.setPrice(Math.round(entity.getUnits() * entity.getCostPerUnit() * 100) / 100.0);

    return converter.convertToDTO(billRepository.save(entity));
  }

  public List<BillDTO> getAllBills(Boolean paid) {
    return billRepository.getAllBills(paid).stream()
        .map(converter::convertToDTO)
        .collect(Collectors.toList());
  }

  public BillDTO createBill(BillDTO bill, HttpServletRequest request) throws Exception {
    validator.validate(bill);
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    var entity = converter.convertToEntity(bill);
    entity.setUser(userEntity);
    entity.setPrice(Math.round(entity.getUnits() * entity.getCostPerUnit() * 100) / 100.0);
    entity.setIssueDate(
        bill.getIssueDate().atOffset(ZoneOffset.UTC).withHour(12).withMinute(0).withSecond(0)
            .truncatedTo(ChronoUnit.SECONDS).toInstant());
    setDates(bill, entity);

    return converter.convertToDTO(billRepository.save(entity));
  }

  private void calculateIndex(BillDTO bill, UserEntity userEntity, BillEntity entity) {
    var lastBill = billRepository.getLastBill(bill.getBillType(), userEntity);
    if (lastBill.isPresent()) {
      entity.setIndex(lastBill.get().getIndex() + entity.getUnits().intValue());
    } else {
      entity.setIndex(entity.getUnits().intValue());
    }
  }

  public List<BillDTO> getBillsPerType(String billType, Boolean paid, HttpServletRequest request)
      throws Exception {
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    return billRepository.getBillsPerTypeAndPaid(BillType.fromString(billType), paid, userEntity)
        .stream()
        .map(converter::convertToDTO)
        .collect(Collectors.toList());
  }

  public List<BillDTO> findAllBills(HttpServletRequest request, Boolean paid) throws Exception {
    var userEntity = userRepository.findByUsername(jwtGenerator.getUsernameFromRequest(request))
        .orElseThrow(Exception::new);

    return billRepository.findAllBills(paid, userEntity)
        .stream()
        .map(converter::convertToDTO)
        .collect(Collectors.toList());
  }

  private void setDates(BillDTO bill, BillEntity entity) {
    entity.setFromDate(bill.getFromDate().atOffset(ZoneOffset.UTC).with(LocalTime.MIN)
        .truncatedTo(ChronoUnit.SECONDS).toInstant());
    entity.setToDate(bill.getToDate().atOffset(ZoneOffset.UTC).with(LocalTime.MAX)
        .truncatedTo(ChronoUnit.SECONDS).toInstant());
    entity.setDueDate(bill.getDueDate().atOffset(ZoneOffset.UTC).with(LocalTime.MAX)
        .truncatedTo(ChronoUnit.SECONDS).toInstant());
  }

}
