package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Phylum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;


public record ClasseDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome, @Size(max = 800, message = "Il campo descrizione deve contenere massimo 800 caratteri") String descrizione,
                        @URL(message = "il campo img deve essere un url") String img,
                        @Size(max = 800, message = "Il campo storia deve contenere massimo 800 caratteri") String storia,
                        @NotNull(message = "Devi inserire il phylum dal quale deriva la tua classe") String phylum_id){
}