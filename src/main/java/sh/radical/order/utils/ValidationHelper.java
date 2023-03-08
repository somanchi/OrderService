package sh.radical.order.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

@Component
public class ValidationHelper {

    Map<String, Map<String,List<String>>> allowedFilters = new HashMap<>();
   Map<String, Set<String>> allowedSort = new HashMap<>();

    public ValidationHelper() {
        allowedFilters.put("order", Map.of("name", List.of("name","address")) );
        allowedSort.put("order",Set.of("name","address"));
    }
}
