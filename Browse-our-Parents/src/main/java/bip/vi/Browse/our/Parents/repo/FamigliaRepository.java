package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FamigliaRepository extends JpaRepository<Famiglia, UUID> {
    Page<Famiglia> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Famiglia> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Famiglia> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}