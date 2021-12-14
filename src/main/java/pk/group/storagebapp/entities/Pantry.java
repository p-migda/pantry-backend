package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pantry")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pantry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
