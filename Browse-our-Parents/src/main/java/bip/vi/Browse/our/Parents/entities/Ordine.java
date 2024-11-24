package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.OrdineDTO;
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
@Table(name = "ordini")
@JsonIgnoreProperties("famiglie")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    @Lob
    @Column(length = 10000)
    private String descrizione;
    @Lob
    private String storia;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @OneToMany(mappedBy = "ordine")
    private List<Famiglia> famiglie;

    public Ordine(OrdineDTO body, Classe classe) {
        this.classe = classe;
        this.nome = body.nome();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
    }
}