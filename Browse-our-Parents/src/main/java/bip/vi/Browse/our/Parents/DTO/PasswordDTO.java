package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record PasswordDTO(
        @NotNull(message = "la password è obbligatoria!") @Size(message = "la password deve avere almeno 8 caratteri!! ", min = 8)
        @Pattern(
                regexp = ".*[A-Z].*",
                message = "La password deve contenere almeno una lettera maiuscola"
        )
        @Pattern(
                regexp = ".*\\d.*",
                message = "La password deve contenere almeno un numero"
        )
        @Pattern(
                regexp = ".*[\\W_].*",
                message = "La password deve contenere almeno un carattere speciale"
        )
        String password){
}