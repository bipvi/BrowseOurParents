package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Classe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, UUID> {
    @Query("SELECT p FROM Classe p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Classe> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT p FROM Classe p WHERE LOWER(p.descrizione) LIKE LOWER(CONCAT('%', :descrizione, '%'))")
    List<Classe> findByDescrizioneContainingIgnoreCase(String descrizione);

    @Query("SELECT p FROM Classe p WHERE LOWER(p.storia) LIKE LOWER(CONCAT('%', :storia, '%'))")
    List<Classe> findByStoriaContainingIgnoreCase(String storia);
}