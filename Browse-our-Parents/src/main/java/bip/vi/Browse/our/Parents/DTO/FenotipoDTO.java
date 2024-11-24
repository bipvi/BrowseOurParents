package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Specie;
import jakarta.validation.constraints.NotNull;

public record FenotipoDTO(String img,@NotNull(message = "Devi inserire la specie del tuo fenotipo") Specie specie,
                          @NotNull(message = "Il fenotipo deve avere un nome") String nome) {
}