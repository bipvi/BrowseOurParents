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
@JsonIgnoreProperties("famiglie")
public class Ordine extends Item{
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
        if (body.img() != null) this.img = body.img();
    }
}