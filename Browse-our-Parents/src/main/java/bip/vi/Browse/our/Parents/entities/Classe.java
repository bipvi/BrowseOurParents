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
public class Classe extends Item{
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
        if (body.img() != null) {
            this.img = body.img();
        }
    }
}