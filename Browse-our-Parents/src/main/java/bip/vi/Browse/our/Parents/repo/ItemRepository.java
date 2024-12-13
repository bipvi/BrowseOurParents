package bip.vi.Browse.our.Parents.repo;

import bip.vi.Browse.our.Parents.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByNomeContainingIgnoreCase(String nome );

    List<Item> findByDescrizioneContainingIgnoreCase(String descrizione );

    List<Item> findByStoriaContainingIgnoreCase(String storia );

  //  Page<Item> findByDescrizioneContainingIgnoreCaseOrStoriaContainingIgnoreCaseOrNomeContainingIgnoreCase(String descrizione, String storia, String nome, Pageable pageable);
}