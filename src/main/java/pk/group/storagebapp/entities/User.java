package pk.group.storagebapp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "hasło")
    private String password;

    @Column(name = "email")
    private String email;

    /*
    0 - admin
    1 - pracownik
    2 - uzytkownik
     */
    @Column(name = "uprawnienia")
    private Integer permission;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id",referencedColumnName = "id")
    private Worker worker;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private Client client;

}
