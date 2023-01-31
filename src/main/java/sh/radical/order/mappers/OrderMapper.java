package sh.radical.order.mappers;

import org.springframework.stereotype.Component;
import sh.radical.order.entities.Context;
import sh.radical.order.inputs.CreateOrderInput;
import sh.radical.order.inputs.UpdateOrderInput;
import sh.radical.order.models.Order;

@Component
public class OrderMapper {

  public Order updateOrder(
    Context context,
    String orderId,
    UpdateOrderInput updateOrderInput,
    Order existingOrder
  ) {
    existingOrder.setName(updateOrderInput.name);
    existingOrder.setAddress(updateOrderInput.address);
    return existingOrder;
  }

  public Order createOrder(
    Context context,
    CreateOrderInput createOrderInput,
    String orderId
  ) {
    Order order = new Order();
    order.setName(createOrderInput.name);
    order.setAddress(createOrderInput.address);
    order.setOrderId(orderId);
    return order;
  }
}
