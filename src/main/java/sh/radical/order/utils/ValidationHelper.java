package sh.radical.order.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Component
public class ValidationHelper {

    Map<String, List<String>> allowedFiltersForField = new HashMap<>();
    Set<String> allowedSort = new HashSet<>();

    public ValidationHelper() {
        allowedFiltersForField.put("name", List.of("name","address") );
        allowedSort.add("name");
        allowedSort.add("address");
    }
}
