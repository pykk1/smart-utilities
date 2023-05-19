package org.smart.utilities.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.smart.utilities.dto.BillType;
import org.smart.utilities.entity.converters.BillTypeConverter;

@Entity
@Getter
@Setter
@Table(name = "bills")
public class BillEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer id;

  @Column
  @Convert(converter = BillTypeConverter.class)
  private BillType billType;

  @Column
  private Double units;

  @Column
  private Double costPerUnit;

  @Column
  private Instant fromDate;

  @Column
  private Instant toDate;

  @Column
  private Instant issueDate;

  @Column
  private Instant dueDate;

  @Column
  private Integer index;

  @Column
  private Integer userIndex;

  @Column
  private Boolean paid;

  @Column
  private Double price;

  @Column
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private UserEntity user;
}
