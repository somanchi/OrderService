package sh.radical.order.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sh.radical.order.entities.Context;
import sh.radical.order.entities.SearchQuery;
import sh.radical.order.exceptions.OrderNotFound;
import sh.radical.order.inputs.CreateOrderInput;
import sh.radical.order.inputs.UpdateOrderInput;
import sh.radical.order.mappers.OrderMapper;
import sh.radical.order.models.Order;
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

  /*
  * Steps:
  * 1. Parse filters string
  * 2. validate filters
  * 3. parse sort string
  * 4. validate sort fields
  * 5. Query generation
  *
  * TODO:
  * 1. validation of filters fields
  *   a. generate @Filterable annotation for the allowed attributes of the model
  *   b. validate if the operation has enough attributes ex eq operation should have only one value but between operation needs 2 values
  *   c. validate if the operation is allowed on the type of the attribute
  * 2. validation of sort
  *   a. generate @Sortable annotation for the allowed attributes of the model
  *
  * Enhancements:
  * 1. move validation to Higher Oder function
  * */
  public List<Order> getAll(
    Context context,
    String filters,
    String sort
  ) {
    log.info("inside findAll service method");
    List<SearchQuery> searchQueries =  parser.getFilters(filters);
    List<Sort.Order> sortOder = parser.getOrderByFields(sort);
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    for (SearchQuery query: searchQueries) {
      booleanBuilder.and(
              Expressions.predicate(
                      query.getOperation(),
                      Expressions.stringPath(query.getQueryObject()),
                      Expressions.constant(query.getValue())
              ));
    }
    try {
      List<Order> orders = (List<Order>) orderRepository.findAll(booleanBuilder,Sort.by(sortOder));
      return orders;
    }
    catch (Exception e) {
      log.error("failed to get orders");
      throw new OrderNotFound();
    }
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
