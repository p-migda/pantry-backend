package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "cena")
    private BigDecimal value;

    @Column(name = "imgUrl")
    private String imgUrl;
}
