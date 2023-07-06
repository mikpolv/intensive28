package com.mikpolv.intensive28.homework.hibernate.exceptions;

public class DAOException extends RuntimeException {
  public DAOException(String message) {
    super(message);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }
}
