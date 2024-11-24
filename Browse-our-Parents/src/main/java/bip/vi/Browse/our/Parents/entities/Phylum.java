package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.PhylumDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "phylums")
@JsonIgnoreProperties("classi")
public class Phylum {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Lob
    private String descrizione;
    @Lob
    private String storia;

    @ManyToOne
    @JoinColumn(name = "regno_id")
    private Regno regno;

    public Regno getRegno() {
        return this.regno;
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "phylum")
    private List<Classe> classi;

    public Phylum(PhylumDTO body, Regno found){
        this.descrizione = body.descrizione();
        this.name = body.nome();
        this.regno = found;
        this.storia = body.storia();
    }
}