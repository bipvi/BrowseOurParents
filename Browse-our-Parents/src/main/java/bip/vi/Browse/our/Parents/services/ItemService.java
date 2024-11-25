package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    // --------------------- GET -------------------
    public Page<Item> findAll(int page, int size, String sort) {
        if (size > 50) size = 50;
        return this.itemRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Item findById(String id) {
        return this.itemRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Item" + id + " not found"));
    }

    public Page<Item> findByNome (int page, int size, String sort, String nome) {
        if (size > 50) size = 50;
        return this.itemRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Item> findByDesc (int page, int size, String sort, String desc) {
        if (size > 50) size = 50;
        return this.itemRepository.findByDescrizioneContainingIgnoreCase(desc, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Item> findByStoria (int page, int size, String sort, String storia) {
        if (size > 50) size = 50;
        return  this.itemRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

  //  public Page<Item> totalQuery (int page, int size, String sort, String query) {
    //    if (size > 50) size = 50;
      //  return this.itemRepository.findByDescrizioneContainingIgnoreCaseOrStoriaContainingIgnoreCaseOrNomeContainingIgnoreCase(query, query ,query ,PageRequest.of(page, size, Sort.by(sort)));}

}
