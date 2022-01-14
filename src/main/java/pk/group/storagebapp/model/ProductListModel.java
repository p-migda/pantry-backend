package pk.group.storagebapp.model;

import lombok.*;
import pk.group.storagebapp.entities.Product;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListModel {
    private Product product;
    private Integer quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductListModel that = (ProductListModel) o;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
