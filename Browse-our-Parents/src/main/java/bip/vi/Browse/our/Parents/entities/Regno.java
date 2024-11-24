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
public class Regno {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String nome;
    private String descrzione;
    private String storia;

    @ToString.Exclude
    @OneToMany(mappedBy = "regno")
    private List<Phylum> phylums;

    public Regno(RegnoDTO body){
        this.nome = body.nome();
        this.descrzione = body.descrzione();
        this.storia = body.storia();
    }
}
