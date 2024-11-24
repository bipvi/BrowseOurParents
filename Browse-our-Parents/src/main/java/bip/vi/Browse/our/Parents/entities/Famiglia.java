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
@Table(name = "famiglie")
@JsonIgnoreProperties("generi")
public class Famiglia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    private String nome;
    @Lob
    private String descrizione;
    @Lob
    private String storia;

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
    }
}