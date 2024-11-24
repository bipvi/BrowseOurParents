package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, UUID> {
    Page<Classe> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Classe> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Classe> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);
}