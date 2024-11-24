package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Specie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecieRepository extends JpaRepository<Specie, UUID> {
    Page<Specie> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Specie> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);

    @Query("SELECT s FROM Specie s WHERE LOWER(s.nome_comune) LIKE LOWER(CONCAT('%', :nomeComune, '%'))")
    Page<Specie> findByNome_comuneContainingIgnoreCase(String nomeComune, Pageable pageable);
}