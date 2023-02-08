package sh.radical.order.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sh.radical.order.entities.Context;
import sh.radical.order.models.Order;
import sh.radical.order.services.OrderService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/v1/orders")
public class OrderQueryController {

  @Autowired
  OrderService orderService;

  @GetMapping
  public List<Order> selectAll(
    @RequestParam(value = "filters") String filters,
    @RequestParam(value = "sort") String sort
  ) {
    log.info("finding all orders with filters: {} and sorBy: {}",filters,sort);
    Context context = new Context();
    List<Order> existingorders = orderService.getAll(
            context,
            filters,
            sort
    );
    return existingorders;
  }
}
