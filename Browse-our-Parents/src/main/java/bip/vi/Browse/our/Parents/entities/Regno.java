package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.RegnoDTO;
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
@Table(name = "regni")
@JsonIgnoreProperties("phylums")
public class Regno extends Item{
    @ToString.Exclude
    @OneToMany(mappedBy = "regno")
    private List<Phylum> phylums;

    public Regno(RegnoDTO body){
        this.nome = body.nome();
        this.descrizione = body.descrizione();
        this.storia = body.storia();
        if (body.img() != null) this.img = body.img();
    }
}
