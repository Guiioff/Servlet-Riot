package br.com.devgui.exception;

public class RiotApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RiotApiException(String message) {
    super(message);
  }

  public RiotApiException(String message, Throwable cause) {
    super(message, cause);
  }

}
