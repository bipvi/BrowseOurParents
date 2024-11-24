package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.ClasseDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "classi")
@JsonIgnoreProperties("ordini")
public class Classe {
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

    @ManyToOne
    @JoinColumn(name = "phylum_id")
    private Phylum phylum;

    @OneToMany(mappedBy = "classe")
    private List<Ordine> ordini;

    public Classe (ClasseDTO body, Phylum phylum) {
        this.descrizione = body.descrizione();
        this.nome = body.nome();
        this.storia = body.storia();
        this.phylum = phylum;
    }
}