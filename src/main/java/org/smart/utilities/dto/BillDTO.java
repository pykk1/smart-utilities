package org.smart.utilities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import lombok.Data;

@Data
public class BillDTO {

  private Integer id;
  private BillType billType;
  private Double units;
  private Double costPerUnit;
  private Instant fromDate;
  private Instant toDate;
  private Instant issueDate;
  private Instant dueDate;
  private Integer index;
  private Integer userIndex;
  private Boolean paid;
  private Integer clientCode;
  private Double price;
  private String name;

  @JsonProperty("billType")
  public String getBillTypeLabel() {
    return billType.getLabel();
  }
}
