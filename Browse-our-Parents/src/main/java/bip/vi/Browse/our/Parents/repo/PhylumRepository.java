package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Phylum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PhylumRepository extends JpaRepository<Phylum, UUID> {
    List<Phylum> findByNomeContainingIgnoreCase(String nome);

    List<Phylum> findByDescrizioneContainingIgnoreCase(String descrizione );

    List<Phylum> findByStoriaContainingIgnoreCase(String storia);
}