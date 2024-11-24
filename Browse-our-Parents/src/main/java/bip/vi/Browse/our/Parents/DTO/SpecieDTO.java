package bip.vi.Browse.our.Parents.DTO;

import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Genere;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record SpecieDTO(@NotNull(message = "Il campo nome comune non può essere vuoto") String nome_comune, String nome_scientifico, String descrizione,
                        String storia, LocalDate data_di_classificazione, String fenotipo_id, Long esemplari_rimasti,
                        @NotNull String genere_id) {
}