package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.FamigliaDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.FamigliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class FamigliaService extends SetImg {
    @Autowired
    private FamigliaRepository famigliaRepository;
    @Autowired
    private OrdineService ordineService;

    //---------------------------- CRUD -----------------------------------------

    public Famiglia saveFamiglia(FamigliaDTO body){
        Famiglia famiglia = new Famiglia(body, this.ordineService.findOrdineById(body.ordine_id()));
        return this.famigliaRepository.save(famiglia);
    }

    public List<Famiglia> findAllFamiglie(){
        return this.famigliaRepository.findAll();
    }

    public Famiglia findFamigliaById(String id){
        return this.famigliaRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("La famiglia con id " + id + " non esiste"));
    }

    public Famiglia findAndUpdateFamiglia(FamigliaDTO body, String id){
        Famiglia found = this.findFamigliaById(id);
        found.setDescrizione(body.descrizione());
        found.setNome(body.nome());
        found.setStoria(body.storia());
        found.setOrdine(this.ordineService.findOrdineById(body.ordine_id()));
        return this.famigliaRepository.save(found);
    }

    public void findAndDeleteFamiglia(String id){
        Famiglia found = this.findFamigliaById(id);
        this.famigliaRepository.delete(found);
        System.out.println("La famiglia " + found.getNome() + " è stata eliminata");
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Famiglia found = this.findFamigliaById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.famigliaRepository.save(found);
        return url;
    }

    //-------------------------------- Query ----------------------------------------------------

    public List<Famiglia> findFamiglieByNome (String nome){
        return this.famigliaRepository.findByNomeContainingIgnoreCase(nome);
    }

//    public Page<Famiglia> findFamiglieByOrdine (String ordine, int page, int size, String sort){
//        if (size > 25) size = 25;
//        Ordine found =
//        return this.famigliaRepository.findByOrdine()
//    }

    public List<Famiglia> findFamiglieByDescrizione (String descrizione){
        return this.famigliaRepository.findByDescrizioneContainingIgnoreCase(descrizione);
    }

    public List<Famiglia> findFamiglieByStoria (String storia){
        return this.famigliaRepository.findByStoriaContainingIgnoreCase(storia);
    }

    //------------------------------------- Get Ordine -------------------------------------------

    public Ordine getOrdineByFamigliaId (String id){
        return this.findFamigliaById(id).getOrdine();
    }

    public List<Genere> getGeneriByFamigliaId (String id){
        return this.findFamigliaById(id).getGeneri();
    }
}
