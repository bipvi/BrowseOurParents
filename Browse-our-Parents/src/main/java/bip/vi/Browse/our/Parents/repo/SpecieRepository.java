package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, UUID> {
    List<Specie> findByDescrizioneContainingIgnoreCase(String descrizione);

    List<Specie> findByStoriaContainingIgnoreCase(String storia);

    @Query("SELECT e FROM Specie e WHERE LOWER(e.nome_comune) LIKE LOWER(CONCAT('%', :nomeComune, '%')) OR LOWER(e.nome_scientifico) LIKE LOWER(CONCAT('%', :nomeScientifico, '%'))")
    List<Specie> findByNome_comuneContainingIgnoreCaseAndNome_scientificoContainsIgnoreCase(String nomeComune, String nomeScientifico);
}