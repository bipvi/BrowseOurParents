package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.GenereDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Entity
@Table(name = "generi")
@JsonIgnoreProperties("lista_specie")
public class Genere {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    @Lob
    @Column(length = 1000)
    private String descrizione;
    @Lob
    private  String storia;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "famiglia_id")
    private Famiglia famiglia;

    @OneToMany(mappedBy = "genere")
    private List<Specie> lista_specie;

    public Genere (GenereDTO body, Famiglia famiglia) {
        this.descrizione = body.descrizione();
        this.nome = body.nome();
        this.storia = body.storia();
        this.famiglia = famiglia;
    }
}