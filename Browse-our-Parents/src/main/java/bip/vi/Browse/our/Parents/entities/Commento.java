package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.CommentoDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "commento")
public class Commento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime created_at;
    private String contenuto;

    public Commento(Item item, User user, CommentoDTO body) {
        this.item = item;
        this.user = user;
        this.created_at = LocalDateTime.now();
        this.contenuto = body.getContenuto();
    }
}