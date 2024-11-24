package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, UUID> {
    Page<Ordine> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Ordine> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Ordine> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}