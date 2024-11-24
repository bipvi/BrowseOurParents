package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Classe;
import jakarta.validation.constraints.NotNull;


public record OrdineDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome  ,String storia,
                        @NotNull(message = "Devi inserire la classe dal quale deriva il tuo ordine") String classe_id,
                        String descrizione){
}