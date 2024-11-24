package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Phylum;
import jakarta.validation.constraints.NotNull;


public record ClasseDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome, String descrizione,
                        String storia, @NotNull(message = "Devi inserire il phylum dal quale deriva la tua classe") String phylum_id){
}