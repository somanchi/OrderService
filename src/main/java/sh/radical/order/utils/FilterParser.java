package sh.radical.order.utils;

import com.querydsl.core.types.Operator;
import com.querydsl.core.types.Ops;
import org.springframework.stereotype.Component;
import sh.radical.order.entities.Operation;
import sh.radical.order.entities.SearchQuery;
import sh.radical.order.models.Order;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FilterParser {

    // filters example ==> name:EQ:sai;address:EQ:534350

    Map<String, Operator> operations = new HashMap<>() {
        {
            put("EQ", Ops.EQ);
            put("GT", Ops.GT);
            put("LT", Ops.LT);
            put("GTE", Ops.GOE);
            put("LTE", Ops.LOE);
        }
    };

    public List<Field> getDeclaredFields(Class<? extends Object> clz) {
        Map<Class<? extends Object>, List<Field>> cache = new ConcurrentHashMap<>();
        return cache.computeIfAbsent(clz, c -> Stream.of(c.getDeclaredFields()).collect(Collectors.toList()));
    }

    public List<SearchQuery> getFilters(String filters) {
        List<SearchQuery> searchQueries = new ArrayList<>();
        List<Field> fields =  getDeclaredFields(Order.class);
        System.out.println(fields);
        Arrays.stream(filters.split(";")).forEach(filter -> {
            String[] filterParams = filter.split(":");
            String modelValue = filterParams[0];

            String op = filterParams[1];
            String values = filterParams[2];
            searchQueries.add(new SearchQuery(modelValue, operations.get(op.toUpperCase()),values));
        });

        return searchQueries;
    }
}

