package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, UUID> {
    List<Ordine> findByNomeContainingIgnoreCase(String nome );

    List<Ordine> findByDescrizioneContainingIgnoreCase(String descrizione);

    List<Ordine> findByStoriaContainingIgnoreCase(String storia );
}