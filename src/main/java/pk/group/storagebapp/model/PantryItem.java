package pk.group.storagebapp.model;

import lombok.*;
import pk.group.storagebapp.entities.Product;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PantryItem {
    private Product product;
    private Integer quantity;
}
