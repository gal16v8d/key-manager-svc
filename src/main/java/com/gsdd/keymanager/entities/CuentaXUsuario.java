package com.gsdd.keymanager.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Great System Development Dynamic <GSDD> <br>
 *         Alexander Galvis Grisales <br>
 *         alex.galvis.sistemas@gmail.com <br>
 * @version 1.0
 */
@Generated
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "cuentaxusuario",
    uniqueConstraints = @UniqueConstraint(columnNames = {"codigoUsuario", "nombreCuenta"}))
public class CuentaXUsuario implements Serializable {

  private static final long serialVersionUID = -5799982073302238435L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long codigoCuenta;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "codigoUsuario", nullable = false)
  private Usuario usuario;
  @Column(name = "nombreCuenta", nullable = false, length = 64)
  private String nombreCuenta;
  @Column(name = "username", nullable = false, length = 64)
  private String username;
  @Column(name = "password", nullable = false, length = 128)
  private String password;
  @Column(name = "url", nullable = true, length = 512)
  private String url;
  @Temporal(TemporalType.DATE)
  @Column(name = "fecha", nullable = false)
  private Date fecha;
}
