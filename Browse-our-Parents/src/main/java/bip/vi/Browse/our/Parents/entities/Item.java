package bip.vi.Browse.our.Parents.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "items")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class Item {
    @Id
    @GeneratedValue
    protected UUID id;
    @Column(nullable = false)
    protected String nome;
    @Column(length = 800)
    protected String descrizione;
    @Column(length = 800)
    protected String storia;
    protected String img;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private List<Commento> commenti;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favourites")
    private List<User> users;
}
