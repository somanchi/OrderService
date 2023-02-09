package sh.radical.order.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.radical.order.entities.Context;
import sh.radical.order.exceptions.OrderNotFound;
import sh.radical.order.inputs.CreateOrderInput;
import sh.radical.order.inputs.UpdateOrderInput;
import sh.radical.order.mappers.OrderMapper;
import sh.radical.order.models.Order;
import sh.radical.order.repositories.OrderRepositoryCustomImpl;
import sh.radical.order.repositories.OrderRepository;
import sh.radical.order.utils.Parser;

import java.util.List;

@Service
@Slf4j
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderMapper orderMapper;

  @Autowired
  Parser parser;

  public void delete(Context context, String orderId) {
    Order existingOrder = orderRepository.findById(orderId).get();

    if (existingOrder == null) {
      throw new OrderNotFound();
    }
    orderRepository.deleteById(orderId);
  }


  public List<Order> getAll(
    Context context,
    String filters,
    String sort
  ) {
    log.info("inside findAll service method");
    return orderRepository.findAllOrders(filters,sort);
  }


  public Order update(
    Context context,
    String orderId,
    UpdateOrderInput updateOrderInput
  ) {
    Order existingOrder = orderRepository.findById(orderId).get();

    if (existingOrder == null) {
      throw new OrderNotFound();
    }
    Order updatedOrder = orderMapper.updateOrder(
      context,
      orderId,
      updateOrderInput,
      existingOrder
    );
    Order savedOrder = orderRepository.save(updatedOrder);
    return savedOrder;
  }

  public Order create(
    Context context,
    CreateOrderInput createOrderInput,
    String orderId
  ) {
    Order order = orderMapper.createOrder(context, createOrderInput, orderId);
    Order createdOrder = orderRepository.save(order);
    return createdOrder;
  }

  public Order get(Context context, String orderId) {
    Order existingOrder = orderRepository.findById(orderId).get();
    if (existingOrder == null) {
      throw new OrderNotFound();
    }
    return existingOrder;
  }
}
