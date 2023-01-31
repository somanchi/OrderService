package sh.radical.order.models;

import java.lang.Override;
import java.util.UUID;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@QueryEntity
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
