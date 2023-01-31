package sh.radical.order.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchQuery {
    String queryObject;
    Operation operation;
    String value;
}
