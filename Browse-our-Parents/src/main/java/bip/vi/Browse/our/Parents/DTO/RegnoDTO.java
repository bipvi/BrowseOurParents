package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;


public record RegnoDTO(@NotEmpty(message = "Il campo nome non può essere vuoto") String nome, @URL(message = "il campo img deve essere un url") String img, @Size(max = 800, message = "Il campo descrizione deve contenere massimo 800 caratteri") String descrizione,
                       @Size(max = 800, message = "Il campo storia deve contenere massimo 800 caratteri") String storia) {
}