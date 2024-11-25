package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    Page<Item> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Item> findByDescrizioneContainingIgnoreCase(String descrizione, Pageable pageable);

    Page<Item> findByStoriaContainingIgnoreCase(String storia, Pageable pageable);

  //  Page<Item> findByDescrizioneContainingIgnoreCaseOrStoriaContainingIgnoreCaseOrNomeContainingIgnoreCase(String descrizione, String storia, String nome, Pageable pageable);
}