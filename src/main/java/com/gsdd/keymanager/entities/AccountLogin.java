package com.gsdd.keymanager.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * @author Great System Development Dynamic <GSDD> <br> Alexander Galvis
 *         Grisales <br> alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "account_login",
    uniqueConstraints = @UniqueConstraint(columnNames = {"account_id", "account_name"}))
public class AccountLogin implements Serializable {

  @Serial
  private static final long serialVersionUID = -5799982073302238435L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Column(name = "account_name", nullable = false, length = 64)
  private String accountName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "type_id")
  private AccountType accountType;

  @Column(name = "login", nullable = false, length = 64)
  private String login;

  @Column(name = "password", nullable = false, length = 128)
  private String password;

  @Column(name = "url", length = 512)
  private String url;

  @Temporal(TemporalType.DATE)
  @Column(name = "modification_date", nullable = false)
  private Date modificationDate;

}
