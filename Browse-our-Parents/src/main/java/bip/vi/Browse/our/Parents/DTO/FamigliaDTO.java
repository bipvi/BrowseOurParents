package bip.vi.Browse.our.Parents.DTO;


import bip.vi.Browse.our.Parents.entities.Ordine;
import jakarta.validation.constraints.NotNull;

public record FamigliaDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome, String descrizione, String storia,
                          @NotNull(message = "Devi inserire l'ordine dal quale deriva la tua famiglia") String ordine_id){
}