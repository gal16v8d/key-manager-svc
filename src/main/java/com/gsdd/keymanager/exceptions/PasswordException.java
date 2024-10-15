package com.gsdd.keymanager.exceptions;

import java.io.Serial;

public class PasswordException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5321938665805663146L;

  public PasswordException(String msg) {
    super(msg);
  }
}
