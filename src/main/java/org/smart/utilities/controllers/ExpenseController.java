package org.smart.utilities.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.smart.utilities.dto.ExpenseDTO;
import org.smart.utilities.dto.ExpenseType;
import org.smart.utilities.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ExpenseController {

  private final ExpenseService expenseService;
  private final ObjectMapper objectMapper;

  @Autowired
  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
    this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @PostMapping("expense")
  @ResponseStatus(HttpStatus.CREATED)
  public ExpenseDTO createExpense(@RequestPart("expense") String expense,
      @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments,
      HttpServletRequest request) throws Exception {

    var expenseDTO = objectMapper.readValue(expense, ExpenseDTO.class);
    expenseDTO.setDate(Instant.now());
    return expenseService.createExpense(expenseDTO, attachments, request);
  }

  @PostMapping("expense/pay/{expenseId}")
  @ResponseStatus(HttpStatus.OK)
  public void payExpense(@PathVariable Integer expenseId) throws Exception {

    expenseService.payExpense(expenseId);
  }

  @GetMapping("/expense/attachment/{expenseId}/{attachmentId}")
  public ResponseEntity<ByteArrayResource> downloadAttachment(@PathVariable Integer expenseId,
      @PathVariable Integer attachmentId) throws NotFoundException {

    var attachmentEntity = expenseService.downloadAttachment(expenseId,
        attachmentId);
    var resource = new ByteArrayResource(attachmentEntity.getData());

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(attachmentEntity.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + attachmentEntity.getFileName())
        .body(resource);
  }

  @GetMapping("expenses/all")
  @ResponseStatus(HttpStatus.OK)
  public List<ExpenseDTO> getAll(@RequestParam("paid") Boolean paid, HttpServletRequest request)
      throws Exception {
    return expenseService.getExpenses(request, paid);
  }

  @GetMapping("expenses/types")
  @ResponseStatus(HttpStatus.OK)
  public String[] getExpenseTypes() {
    return ExpenseType.labels();
  }
}

