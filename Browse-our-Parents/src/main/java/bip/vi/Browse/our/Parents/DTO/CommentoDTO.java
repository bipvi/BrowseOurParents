package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;


@Value
public class CommentoDTO {
    @NotNull(message = "Collega l'Item a cui si riferisce il commento")
    String item_id;
    @NotNull(message = "Devi inserire il campo user")
    String user_id;
    @NotNull(message = "Non puoi scrivere un commento vuoto")
    @Size(message = "Il contenuto del commento deve essere massimo di 255 caratteri", max = 255)
    String contenuto;
}