package sh.radical.order.repositories;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sh.radical.order.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, String>, QuerydslPredicateExecutor<Order>,OrderRepositoryCustom {
}
