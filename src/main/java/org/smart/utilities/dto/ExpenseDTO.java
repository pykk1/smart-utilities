package org.smart.utilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.List;
import lombok.Data;

@Data
public class ExpenseDTO {

  private Integer id;
  private ExpenseType expenseType;
  private String title;
  private List<AttachmentDTO> attachments;
  private Boolean paid;
  private Double price;
  private String note;
  private Instant date;
  private Integer clientCode;

  @JsonProperty("expenseType")
  public String getExpenseTypeLabel() {
    return expenseType.getLabel();
  }
}
