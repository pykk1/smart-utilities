package org.smart.utilities.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.smart.utilities.dto.ExpenseType;
import org.smart.utilities.entity.converters.ExpenseTypeConverter;

@Entity
@Getter
@Setter
@Table(name = "expenses")
public class ExpenseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer id;

  @Column
  @Convert(converter = ExpenseTypeConverter.class)
  private ExpenseType expenseType;

  @Column
  private String title;

  @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<AttachmentEntity> attachments;

  @Column
  private Boolean paid;

  @Column
  private Double price;

  @Column
  private String notes;

  @Column
  private Instant date;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;

  public List<AttachmentEntity> getAttachments() {
    if (attachments == null) {
      attachments = new ArrayList<>();
    }
    return attachments;
  }

}
