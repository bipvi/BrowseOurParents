package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Phylum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhylumRepository extends JpaRepository<Phylum, UUID> {
    Page<Phylum> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Phylum> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Phylum> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}