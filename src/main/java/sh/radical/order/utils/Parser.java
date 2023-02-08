package sh.radical.order.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import sh.radical.order.entities.SearchQuery;
import sh.radical.order.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Parser {

    // filters example ==> name:EQ:sai;address:EQ:534350

//    TODO:
//     validation of filters fields
//         a. generate @Filterable annotation for the allowed attributes of the model
//         b. validate if the operation has enough attributes ex eq operation should have only one value but between operation needs 2 values
//         c. validate if the operation is allowed on the type of the attribute

    Constants constants = new Constants();
    public List<SearchQuery> getFilters(String filters) {
        List<SearchQuery> searchQueries = new ArrayList<>();
        Arrays.stream(filters.split(";")).forEach(filter -> {
            String[] filterParams = filter.split(":");
            String modelValue = filterParams[0];

            String op = filterParams[1];
            String values = filterParams[2];
            searchQueries.add(new SearchQuery(modelValue, constants.operations.get(op.toUpperCase()),values));
        });

        return searchQueries;
    }

    public List<Sort.Order> getOrderByFields(String sort) {
        List<Sort.Order> sorts= new ArrayList<>();
        Arrays.stream(sort.split(";")).forEach(it -> {
            if (it.charAt(0) == '+' || it.charAt(0) == '-') {
                Sort.Direction direction = getSortOperation(it.charAt(0));
                String sortObject = sort.substring(1);
                sorts.add(new Sort.Order(direction,sortObject));
            }
            else {
                throw new ParserException("Failed to parse the sort field" + it);
            }
        });
        return sorts;
    }

    private Sort.Direction getSortOperation (char operation) {
        if (operation == '+') {
            return Sort.Direction.ASC;
        }
        else if (operation == '-') {
            return Sort.Direction.DESC;
        }
        return null;
    }
}

