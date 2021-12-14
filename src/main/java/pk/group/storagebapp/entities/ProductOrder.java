package pk.group.storagebapp.entities;

import lombok.*;
import pk.group.storagebapp.keys.ProductOrderKey;

import javax.persistence.*;

@Entity
@Table(name = "pruductOrder")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(value = ProductOrderKey.class)
public class ProductOrder {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "ilosc")
    private Integer quantity;
}
