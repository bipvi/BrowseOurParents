package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Regno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegnoRepository extends JpaRepository<Regno, UUID> {
    List<Regno> findByNomeContainingIgnoreCase(String nome);

    List<Regno> findByDescrizioneContainingIgnoreCase(String descrizione);

    List<Regno> findByStoriaContainingIgnoreCase(String storia);
}