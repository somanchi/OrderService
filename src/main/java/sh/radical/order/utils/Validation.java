package sh.radical.order.utils;

import org.springframework.stereotype.Component;
import sh.radical.order.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Validation {

    ValidationHelper validationHelper = new ValidationHelper();

    public void validateFilters(String modelValue, String operation) {
        List<String> errorMessages = new ArrayList<>();
        List<String> allowedOperations = validationHelper.allowedFilters.get("order").get(modelValue);
        if (allowedOperations != null) {
           boolean isFilterAllowed = allowedOperations.contains(operation.toUpperCase());
           if (! isFilterAllowed) {
               errorMessages.add ("operation: "+ operation + "is not allowed for the field");
           }
        }
        else {
            errorMessages.add( modelValue + " field is not allowed to filter");
        }

        if (! errorMessages.isEmpty()) {
            throw new ValidationException("Validation failed" + String.join(",", errorMessages));
        }
    }

    public void validateSort (String sortOperation) {
        List<String> errorMessages = new ArrayList<>();
        if (! validationHelper.allowedSort.get("order").contains(sortOperation)) {
            errorMessages.add( sortOperation+ "is not allowed for sorting");
        }
        if (! errorMessages.isEmpty()) {
            throw new ValidationException("Validation failed" + String.join(",", errorMessages));
        }
    }
}
