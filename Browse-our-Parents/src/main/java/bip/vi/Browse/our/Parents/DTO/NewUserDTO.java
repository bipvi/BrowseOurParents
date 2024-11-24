package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

public record NewUserDTO(@NotNull(message = "Il campo username non può essere vuoto")
                         @Size( min=3, max=15, message = "il numero di caratteri dell'username dev'essere tra i 3 e i 15 caratteri ")
                         String username,
                         @NotNull(message = "Devi inserire la password")
                         @Size( min=8, message = "la password deve avere almeno 8 caratteri!! ")
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
                         String password,
                         String avatar){
}