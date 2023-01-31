package sh.radical.order.repositories;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import sh.radical.order.models.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String>, QuerydslPredicateExecutor<Order> {
}
