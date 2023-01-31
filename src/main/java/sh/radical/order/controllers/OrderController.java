package sh.radical.order.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sh.radical.order.entities.Context;
import sh.radical.order.inputs.CreateOrderInput;
import sh.radical.order.inputs.UpdateOrderInput;
import sh.radical.order.models.Order;
import sh.radical.order.services.OrderService;

@Slf4j
@RestController
@RequestMapping(value = "/v1/orders")
public class OrderController {

  @Autowired
  OrderService orderService;

  @DeleteMapping(value = "/{orderId}")
  public void delete(@PathVariable(value = "orderId") String orderId) {
    log.info("Received a delete request for Order {} ", orderId);
    Context context = new Context();
    orderService.delete(context, orderId);
    log.info("Delete request completed for Order {} ", orderId);
  }

  @PutMapping(value = "/{orderId}")
  public ResponseEntity update(
    @PathVariable(value = "orderId") String orderId,
    @RequestBody UpdateOrderInput updateOrderInput
  ) {
    log.info("Received a update request for Order {} ", orderId);
    Context context = new Context();
    orderService.update(context, orderId, updateOrderInput);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Location", "/" + orderId);
    log.info("Update request for Order {} is complete", orderId);
    return new ResponseEntity(null, responseHeaders, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity create(@RequestBody CreateOrderInput createOrderInput) {
    Context context = new Context();
    log.info("Received a new create request");
    var id = Order.getNewOrderId();
    orderService.create(context, createOrderInput, id);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Location", "orders/" + id);
    log.info("Create request for Order - {} is complete", id);
    return new ResponseEntity(null, responseHeaders, HttpStatus.OK);
  }

  @GetMapping(value = "/{orderId}")
  public Order get(@PathVariable(value = "orderId") String orderId) {
    log.info("Received a get request for Order {} ", orderId);
    Context context = new Context();
    Order existingOrder = orderService.get(context, orderId);
    log.info("Get request for Order {} is complete ", orderId);
    return existingOrder;
  }
}
