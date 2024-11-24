package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.FenotipoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fenotipi")
@JsonIgnoreProperties("specie")
public class Fenotipo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String img;
    private String nome;

    @OneToOne
    private Specie specie;

    public Fenotipo(FenotipoDTO body){
        this.img = body.img();
        this.nome = body.nome();
        this.specie = body.specie();
    }
}
