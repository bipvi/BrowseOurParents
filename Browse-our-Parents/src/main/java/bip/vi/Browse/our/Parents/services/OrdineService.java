package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.OrdineDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class OrdineService extends SetImg {
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private ClasseService classeService;

    //--------------------------------- Crud ------------------------------------

    public Ordine saveOrdine(OrdineDTO body) {
        Ordine ordine = new Ordine(body, this.classeService.findClasseById(body.classe_id()));
        return this.ordineRepository.save(ordine);
    }

    public List<Ordine> findAllOrdini() {
        return this.ordineRepository.findAll();
    }

    public Ordine findOrdineById(String id) {
        return this.ordineRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("L'ordine con id: " + id + " non trovato "));
    }

    public Ordine findOrdineAndUpdate(String id, OrdineDTO body) {
        Ordine found = this.findOrdineById(id);
        found.setClasse(this.classeService.findClasseById(body.classe_id()));
        found.setNome(body.nome());
        found.setStoria(body.storia());
        found.setDescrizione(body.descrizione());
        return this.ordineRepository.save(found);
    }

    public void findOrdineAndDelete(String id) {
        Ordine found = this.findOrdineById(id);
        this.ordineRepository.delete(found);
        System.out.println("L' ordine " + found.getNome() + " eliminata con successo");
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Ordine found = this.findOrdineById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.ordineRepository.save(found);
        return url;
    }

    //----------------------------- Query ---------------------------------------------------

    public List<Ordine> findOrdiniByNome(String nome) {
        return this.ordineRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Ordine> findOrdiniByDescrizione( String descrizione) {
        return this.ordineRepository.findByDescrizioneContainingIgnoreCase(descrizione);
    }

    public List<Ordine> findOrdiniByStoria(String storia) {
        return this.ordineRepository.findByStoriaContainingIgnoreCase(storia);
    }

    //------------------------------------ Get Classe -----------------------------------------------

    public Classe getClasseByOrdineId(String id) {
        return this.findOrdineById(id).getClasse();
    }

    public List<Famiglia> findFamigliaByOrdineId(String id) { return  this.findOrdineById(id).getFamiglie(); }
}
