package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.CommentoDTO;
import bip.vi.Browse.our.Parents.entities.Commento;
import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.CommentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentoService {
    @Autowired
    private CommentoRepository commentoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    // -------------------- Crud --------------------

    public Commento save(CommentoDTO body){
        Item itemFound = this.itemService.findById(body.item_id());
        User userFound = this.userService.findUserById(body.user_id());
        Commento commento = new Commento(itemFound, userFound, body);
        return this.commentoRepository.save(commento);
    }

    public List<Commento> findAll(){
        return this.commentoRepository.findAll();
    }

    public Commento findById(int id){
        return this.commentoRepository.findById(id).orElseThrow(() -> new NotFoundException("Il commento con id " + id + " nn trovato"));
    }

    public void deleteCommento(int id){
        this.commentoRepository.deleteById(id);
    }

    // -------------------- Update contenuto ------------------

    public Commento updateContenuto (String contenuto, int id){
        Commento commento = this.findById(id);
        commento.setContenuto(contenuto);
        return this.commentoRepository.save(commento);
    }

    // -------------------- GetCommentsByItemId ----------------------

    public List<Commento> findByItem(String id){
        return this.commentoRepository.findByItem_Id(UUID.fromString(id));
    }
}
