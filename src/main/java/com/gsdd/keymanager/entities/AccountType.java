package com.gsdd.keymanager.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "account_type")
public class AccountType implements Serializable {
  
  private static final long serialVersionUID = 1255832430684376543L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "type_id")
  private Long typeId;
  
  @Column(name = "name", unique = true, nullable = false, length = 64)
  private String name;

}
