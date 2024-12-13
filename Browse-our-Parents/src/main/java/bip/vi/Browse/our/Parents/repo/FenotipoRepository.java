package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Fenotipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface FenotipoRepository extends JpaRepository<Fenotipo, UUID> {

    List<Fenotipo> findByNomeContainingIgnoreCase(String nome);
}