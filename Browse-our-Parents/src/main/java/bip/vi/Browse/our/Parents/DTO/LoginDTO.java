package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.*;

public record LoginDTO(
        @NotNull(message = "l'username è obbligatorio!")
        @Size( min=3, max=15, message = "il numero di caratteri dell'username dev'essere tra i 3 e i 15 caratteri ")
        String username,
        @NotNull(message = "la password è obbligatoria!")
        @Size( min=8, message = "la password deve avere almeno 8 caratteri!! ")
        String password
) {
}
