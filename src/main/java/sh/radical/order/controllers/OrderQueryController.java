package sh.radical.order.controllers;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sh.radical.order.entities.Context;
import sh.radical.order.models.Order;
import sh.radical.order.services.OrderService;

@Slf4j
@RestController
@RequestMapping(value = "/v1/orders")
public class OrderQueryController {

  @Autowired
  OrderService orderService;

  @GetMapping
  public List selectAllByNameAndOrderId(
    @RequestParam(value = "filters") String filters
  ) {
    Context context = new Context();
    List<Order> existingorders = orderService.getByNameAndOrderId(
      context,
      filters
    );
    return existingorders;
  }
}
