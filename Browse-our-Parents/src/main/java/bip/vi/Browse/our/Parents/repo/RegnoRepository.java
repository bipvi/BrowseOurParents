package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Regno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegnoRepository extends JpaRepository<Regno, UUID> {
    Page<Regno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Regno> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Regno> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}