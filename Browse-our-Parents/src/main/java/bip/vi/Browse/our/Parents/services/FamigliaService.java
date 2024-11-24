package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.FamigliaDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.FamigliaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FamigliaService {
    @Autowired
    private FamigliaRepository famigliaRepository;
    @Autowired
    private OrdineService ordineService;

    //---------------------------- CRUD -----------------------------------------

    public Famiglia saveFamiglia(FamigliaDTO body){
        Famiglia famiglia = new Famiglia(body, this.ordineService.findOrdineById(body.ordine_id()));
        return this.famigliaRepository.save(famiglia);
    }

    public Page<Famiglia> findAllFamiglie(int page, int size, String sort){
        if (size > 25) size = 25;
        return this.famigliaRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
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

    //-------------------------------- Query ----------------------------------------------------

    public Page<Famiglia> findFamiglieByNome (String nome, int page, int size, String sort){
        if (size > 25) size = 25;
        return this.famigliaRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size, Sort.by(sort)));
    }

//    public Page<Famiglia> findFamiglieByOrdine (String ordine, int page, int size, String sort){
//        if (size > 25) size = 25;
//        Ordine found =
//        return this.famigliaRepository.findByOrdine()
//    }

    public Page<Famiglia> findFamiglieByDescrizione (String descrizione, int page, int size, String sort){
        if (size > 25) size = 25;
        return this.famigliaRepository.findByDescrizioneContainingIgnoreCase(descrizione, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Famiglia> findFamiglieByStoria (String storia, int page, int size, String sort){
        if (size > 25) size = 25;
        return this.famigliaRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

    //------------------------------------- Get Ordine -------------------------------------------

    public Ordine getOrdineByFamigliaId (String id){
        return this.findFamigliaById(id).getOrdine();

    }

}
