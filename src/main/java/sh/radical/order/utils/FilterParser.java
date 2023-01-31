package sh.radical.order.utils;

import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import org.springframework.stereotype.Component;
import sh.radical.order.entities.Operation;
import sh.radical.order.entities.SearchQuery;

import java.util.*;

@Component
public class FilterParser {

    // filters example ==> name:EQ:sai;address:EQ:534350

    Map<String, Operator> operations = new HashMap<String, Operator>()
    {
        {
            put("EQ", Ops.EQ);
            put("GT", Ops.GT);
        }
    };

    public List<SearchQuery> getFilters(String filters) {
        List<SearchQuery> searchQueries = new ArrayList<>();
        Arrays.stream(filters.split(";")).forEach(filter -> {
            String[] filterParams = filter.split(":");
            String modelValue = filterParams[0];
            String op = filterParams[1];
            String values = filterParams[2];
            searchQueries.add(new SearchQuery(modelValue, operations.get(op),values));

        });
        return searchQueries;
    }
}

