package sh.radical.order.utils;

import org.springframework.stereotype.Component;
import sh.radical.order.entities.Operation;
import sh.radical.order.entities.SearchQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FilterParser {

    // filters example ==> name:EQ:sai;address:EQ:534350

    public List<SearchQuery> getFilters(String filters) {
        List<SearchQuery> searchQueries = new ArrayList<>();
        Arrays.stream(filters.split(";")).forEach(filter -> {
            String[] filterParams = filter.split(":");
            String modelValue = filterParams[0];
            String op = filterParams[1];
            String values = filterParams[2];
            searchQueries.add(new SearchQuery(modelValue, Operation.valueOf(op),values));

        });
        return searchQueries;
    }
}
