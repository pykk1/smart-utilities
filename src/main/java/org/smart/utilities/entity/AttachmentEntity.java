package org.smart.utilities.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "attachments")
public class AttachmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer id;

  @Lob
  @Column
  private byte[] data;

  @Column
  private String fileName;

  @Column
  private String fileType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id", referencedColumnName = "id")
  private ExpenseEntity expense;

}
