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
public class Specie extends Item{


    private String nome_scientifico;
    private String nome_comune;
    @Column(length = 800)
    private String descrizione;
    @Column(length = 800)
    private String storia;
    private int anno_di_classificazione;
    @OneToOne(cascade = CascadeType.ALL)
    private Fenotipo fenotipo;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "genere_id")
    private Genere genere;

    public Specie(SpecieDTO body, Fenotipo fenotipo, Genere genere) {
        this.nome_comune = body.nome_comune();
        this.nome_scientifico = body.nome_scientifico();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        this.anno_di_classificazione = body.anno_di_classificazione();
        this.fenotipo = fenotipo;
        if (body.img() != null) this.img = body.img();
        this.genere = genere;
    }

    public Specie(SpecieDTO body, Genere genere) {
        this.nome_comune = body.nome_comune();
        this.nome = this.nome_comune;
        this.nome_scientifico = body.nome_scientifico();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        if (body.img() != null) this.img = body.img();
        this.anno_di_classificazione = body.anno_di_classificazione();
        this.genere = genere;
    }
}