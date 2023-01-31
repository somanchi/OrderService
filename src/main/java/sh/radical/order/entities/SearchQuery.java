package sh.radical.order.entities;

import com.querydsl.core.types.Operator;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchQuery {
    String queryObject;
    Operator operation;
    String value;
}
