package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pantryitem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PantryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nazwa")
    private String name;

    @Column(name = "ilość")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pantry_id", referencedColumnName = "id")
    private Pantry pantry;
}
