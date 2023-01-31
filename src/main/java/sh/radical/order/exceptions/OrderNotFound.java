package sh.radical.order.exceptions;

import java.lang.Throwable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import sh.radical.order.exceptions.OrderCustomException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OrderNotFound extends OrderCustomException {

  public OrderNotFound() {
    super();
  }

  public OrderNotFound(String message) {
    super(message);
  }

  public OrderNotFound(String message, Throwable t) {
    super(message, t);
  }
}
