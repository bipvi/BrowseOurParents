package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Genere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GenereRepository extends JpaRepository<Genere, UUID> {
    List<Genere> findByNomeContainingIgnoreCase(String nome );

    List<Genere> findByDescrizioneContainingIgnoreCase(String descrizione );

    List<Genere> findByStoriaContainingIgnoreCase(String storia );
}