package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.FamigliaDTO;
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
@JsonIgnoreProperties("generi")
public class Famiglia extends Item{
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ordine_id")
    private Ordine ordine;

    @OneToMany(mappedBy = "famiglia")
    private List<Genere> generi;

    public Famiglia (FamigliaDTO body, Ordine ordine){
        this.nome = body.nome();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        this.ordine = ordine;
        if (body.img() != null) this.img = body.img();
    }
}