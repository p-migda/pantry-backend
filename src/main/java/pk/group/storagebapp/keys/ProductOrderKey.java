package pk.group.storagebapp.keys;

import lombok.*;
import pk.group.storagebapp.entities.Order;
import pk.group.storagebapp.entities.Product;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderKey implements Serializable {
    private Product product;
    private Order order;
}
