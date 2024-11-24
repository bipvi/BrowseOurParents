package bip.vi.Browse.our.Parents.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


public record RegnoDTO(@NotEmpty(message = "Il campo nome non può essere vuoto") String nome, String descrzione,
                       String storia) {
}