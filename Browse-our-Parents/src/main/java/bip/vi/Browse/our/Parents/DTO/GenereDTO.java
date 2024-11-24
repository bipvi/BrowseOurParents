package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Famiglia;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


public record GenereDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome, String descrizione,
                        String storia,
                        @NotNull(message = "Devi inserire la famiglia dal quale deriva il tuo Genere") String famiglia_id) {
}