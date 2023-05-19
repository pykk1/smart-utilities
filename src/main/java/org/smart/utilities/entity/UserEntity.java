package org.smart.utilities.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Integer id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String username;

  @Column
  private String password;

  @Column(name = "client_code")
  private Integer clientCode;

  @Column(name = "billing_code")
  private Integer billingCode;

  @Column
  private String address;

  @Column
  private String email;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private List<RoleEntity> roles = new ArrayList<>();
}
