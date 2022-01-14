package pk.group.storagebapp.model;

import lombok.*;
import pk.group.storagebapp.entities.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductModel {
    private Integer quantity;
    private Product product;
}
