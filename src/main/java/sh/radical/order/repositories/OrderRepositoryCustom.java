package sh.radical.order.repositories;

import sh.radical.order.models.Order;

import java.util.List;

public interface OrderRepositoryCustom {
     List<Order> findAllOrders(String filters, String sort);
}
