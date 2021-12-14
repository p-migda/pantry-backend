package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "worker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imie")
    private String firstname;

    @Column(name = "nazwisko")
    private String lastname;

    @Column(name = "stanowisko")
    private String position;

}
