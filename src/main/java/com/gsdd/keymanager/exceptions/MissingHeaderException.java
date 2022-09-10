package com.gsdd.keymanager.exceptions;

public class MissingHeaderException extends RuntimeException {

  private static final long serialVersionUID = -5321938665805663146L;

  public MissingHeaderException(String msg) {
    super(msg);
  }
}
