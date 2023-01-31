package sh.radical.order.exceptions;

import java.lang.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sh.radical.order.exceptions.OrderNotFound;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = OrderNotFound.class)
  public ResponseEntity handleOrderNotFound(OrderNotFound exception) {
    return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity genericExceptionHandler(Exception exception) {
    return new ResponseEntity(
      "Internal Server Error",
      HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
