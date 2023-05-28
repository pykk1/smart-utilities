package org.smart.utilities.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
  private String note;

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
