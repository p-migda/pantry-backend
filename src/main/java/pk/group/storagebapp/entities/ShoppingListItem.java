package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shoppingListItem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "ilosc")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingList_id", referencedColumnName = "id")
    private ShoppingList shoppingList;
}
