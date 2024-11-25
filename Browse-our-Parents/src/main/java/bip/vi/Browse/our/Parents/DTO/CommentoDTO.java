package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record CommentoDTO (@NotNull(message = "Collega l'Item a cui si riferisce il commento")
                           String item_id,
                           @NotNull(message = "Devi inserire il campo user")
                           int user_id,
                           @NotNull(message = "Non puoi scrivere un commento vuoto")
                           @Size(message = "Il contenuto del commento deve essere massimo di 255 caratteri", max = 255)
                           String contenuto){

}