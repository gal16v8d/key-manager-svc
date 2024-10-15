package com.gsdd.keymanager.exceptions;

import java.io.Serial;

public class MissingHeaderException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5321938665805663146L;

  public MissingHeaderException(String msg) {
    super(msg);
  }
}
