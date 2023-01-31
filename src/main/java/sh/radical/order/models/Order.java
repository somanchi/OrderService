package sh.radical.order.models;

import java.lang.Override;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class Order {

  @Getter
  @Setter
  String name;

  @Getter
  @Setter
  @Id
  String orderId;

  @Getter
  @Setter
  String address;

  @Override
  public String toString() {
    return orderId;
  }

  public static String getNewOrderId() {
    return UUID.randomUUID().toString();
  }
}
