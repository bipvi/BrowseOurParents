package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.SpecieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Entity
@Table(name = "specie")
public class Specie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome_scientifico;
    private String nome_comune;
    @Lob
    @Column(length = 10000)
    private String descrizione;
    @Lob
    private String storia;
    private LocalDate data_di_classificazione;
    @OneToOne(cascade = CascadeType.ALL)
    private Fenotipo fenotipo;
    private long esemplari_rimasti;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "genere_id")
    private Genere genere;

    public Specie(SpecieDTO body, Fenotipo fenotipo, Genere genere) {
        this.nome_comune = body.nome_comune();
        this.nome_scientifico = body.nome_scientifico();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        this.data_di_classificazione = body.data_di_classificazione();
        this.fenotipo = fenotipo;
        this.genere = genere;
    }

    public Specie(SpecieDTO body, Genere genere) {
        this.nome_comune = body.nome_comune();
        this.nome_scientifico = body.nome_scientifico();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        this.data_di_classificazione = body.data_di_classificazione();
        this.genere = genere;
    }
}