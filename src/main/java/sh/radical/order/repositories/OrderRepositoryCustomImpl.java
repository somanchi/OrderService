package sh.radical.order.repositories;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import sh.radical.order.entities.SearchQuery;
import sh.radical.order.exceptions.OrderNotFound;
import sh.radical.order.models.Order;
import sh.radical.order.utils.Parser;

import java.util.List;

@Repository
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @Autowired
    Parser parser;

    @Autowired
    @Lazy
    OrderRepository orderRepository;

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

    @Override
    public List<Order> findAllOrders( String filters, String sort) {
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
            throw new OrderNotFound("failed to fetch orders with filters "+ filters + " and order by: "+ sort);
        }
    }
}
