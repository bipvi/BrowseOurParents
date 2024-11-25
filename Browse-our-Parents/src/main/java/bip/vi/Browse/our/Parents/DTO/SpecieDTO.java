package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Genere;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;


public record SpecieDTO(@NotNull(message = "Il campo nome comune non può essere vuoto") String nome_comune, String nome_scientifico,@URL(message = "il campo img deve essere un url") String img,
                        @Size(max = 800, message = "Il campo descrizione deve contenere massimo 800 caratteri") String descrizione,
                        @Size(max = 800, message = "Il campo storia deve contenere massimo 800 caratteri") String storia, int anno_di_classificazione, String fenotipo_id, Long esemplari_rimasti,
                        @NotNull(message = "Inserisci il genere") String genere_id) {
}