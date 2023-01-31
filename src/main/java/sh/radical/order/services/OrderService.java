package sh.radical.order.services;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.radical.order.entities.Context;
import sh.radical.order.entities.SearchQuery;
import sh.radical.order.exceptions.OrderNotFound;
import sh.radical.order.inputs.CreateOrderInput;
import sh.radical.order.inputs.UpdateOrderInput;
import sh.radical.order.mappers.OrderMapper;
import sh.radical.order.models.Order;
import sh.radical.order.models.QOrder;
import sh.radical.order.repositories.OrderRepository;
import sh.radical.order.utils.FilterParser;

@Service
public class OrderService {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderMapper orderMapper;

  @Autowired
  FilterParser filterParser;

  public void delete(Context context, String orderId) {
    Order existingOrder = orderRepository.findById(orderId).get();

    if (existingOrder == null) {
      throw new OrderNotFound();
    }
    orderRepository.deleteById(orderId);
  }

  public List getByNameAndOrderId(
    Context context,
    String filters
  ) {

    // parseFilters
    // validateFilters

    List<SearchQuery> searchQueries =  filterParser.getFilters(filters);
    QOrder qOrder = new QOrder("order");
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    for (SearchQuery query: searchQueries) {
      booleanBuilder.and()
    }
    return new ArrayList();
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
