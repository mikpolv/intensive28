package com.mikpolv.intensive28.homework.hibernate.exceptions;

public class ServiceException extends RuntimeException {
  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
