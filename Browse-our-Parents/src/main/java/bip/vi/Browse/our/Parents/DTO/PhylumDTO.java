package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Regno;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PhylumDTO(@NotNull(message = "Il campo nome non può essere vuoto") String nome,@Size(max = 255, message = "La descrizione deve avere massimo 255 caratteri") String descrizione,
                        @Size(max = 255, message = "La storia deve avere massimo 255 caratteri")String storia, @NotNull String regno_id){
}