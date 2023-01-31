package sh.radical.order.exceptions;

import java.lang.RuntimeException;
import java.lang.Throwable;

public class OrderCustomException extends RuntimeException {

  public OrderCustomException() {
    super();
  }

  public OrderCustomException(String message) {
    super(message);
  }

  public OrderCustomException(String message, Throwable t) {
    super(message, t);
  }
}
