package br.com.devgui.exception;

public class DatadragonException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public DatadragonException(String message) {
    super(message);
  }

  public DatadragonException(String message, Throwable cause) {
    super(message, cause);
  }

}
