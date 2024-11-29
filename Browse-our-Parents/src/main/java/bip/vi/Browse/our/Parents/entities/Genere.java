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
@JsonIgnoreProperties("lista_specie")
public class Genere extends Item{
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
        if (body.img() != null) this.img = body.img();
    }
}