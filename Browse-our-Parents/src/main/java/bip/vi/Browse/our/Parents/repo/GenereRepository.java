package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Genere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenereRepository extends JpaRepository<Genere, UUID> {
    Page<Genere> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Genere> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Genere> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}