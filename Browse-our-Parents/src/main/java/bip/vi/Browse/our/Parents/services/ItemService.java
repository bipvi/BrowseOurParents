package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.ItemRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Cloudinary cloudinary;

    // --------------------- GET -------------------
    public Page<Item> findAll(int page, int size, String sort) {
        if (size > 50) size = 50;
        return this.itemRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Item findById(String id) {
        return this.itemRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Item" + id + " not found"));
    }

    public List<Item> findByNome (String nome) {
        return this.itemRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Item> findByDesc (String desc) {
        return this.itemRepository.findByDescrizioneContainingIgnoreCase(desc);
    }

    public List<Item> findByStoria (String storia) {
        return  this.itemRepository.findByStoriaContainingIgnoreCase(storia);
    }

  //  public Page<Item> totalQuery (int page, int size, String sort, String query) {
    //    if (size > 50) size = 50;
      //  return this.itemRepository.findByDescrizioneContainingIgnoreCaseOrStoriaContainingIgnoreCaseOrNomeContainingIgnoreCase(query, query ,query ,PageRequest.of(page, size, Sort.by(sort)));}

    // ------------------ LetMeExploreRandomly ----------------------------

    public Item findRandomly(){
        Random random = new Random();
        List<Item> items = this.itemRepository.findAll();
        return items.get(random.nextInt(items.size()));
    }

    public String setImg(String id, MultipartFile file){
        Item f = this.findById(id);
        String url = null;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            f.setImg(url);
            itemRepository.save(f);
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }

        return url;
    }

}
