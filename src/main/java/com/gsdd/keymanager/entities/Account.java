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

/**
 * @author Great System Development Dynamic <GSDD> <br>
 *     Alexander Galvis Grisales <br>
 *     alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "account")
public class Account implements Serializable {

  private static final long serialVersionUID = 2383390124298097984L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "first_name", nullable = false, length = 16)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 16)
  private String lastName;

  @Column(name = "login", unique = true, nullable = false, length = 64)
  private String login;

  @Column(name = "password", nullable = false, length = 128)
  private String password;

  @Column(name = "role", nullable = false)
  private Long role;
}
