package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.PhylumDTO;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.entities.Regno;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.PhylumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class PhylumService extends SetImg{
    @Autowired
    private PhylumRepository phylumRepository;
    @Autowired
    private RegnoService regnoService;

    //--------------------------- Crud -------------------------------------------

    public Phylum save(PhylumDTO body){
        Regno found = this.regnoService.findRegnoById(body.regno_id());
        Phylum phylum = new Phylum(body, found);
        return this.phylumRepository.save(phylum);
    }

    public Page<Phylum> findAllPhylum(int page, int size, String sort){
        if (size > 5) size = 5;
        return this.phylumRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Phylum findPhylumById(String id){
        return this.phylumRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Il phylum con id: " + id + "non è stato trovato"));
    }

    public Phylum findPhylumAndUpdate(String id, PhylumDTO body){
        Phylum found = this.findPhylumById(id);
        found.setDescrizione(body.descrizione());
        found.setStoria(body.storia());
        found.setRegno(this.regnoService.findRegnoById(body.regno_id()));
        found.setNome(body.nome());
        return this.phylumRepository.save(found);
    }

    public void findPhylumAndDelete(String id){
        Phylum found = this.findPhylumById(id);
        this.phylumRepository.delete(found);
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Phylum found = this.findPhylumById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.phylumRepository.save(found);
        return url;
    }

    //------------------------ Query ---------------------------------------------------------

    public Page<Phylum> findPhylumQueryNome(int page, int size, String sort, String nome) {
        if (size > 5) size = 5;
        return this.phylumRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Phylum> findPhylumByDescrizione(int page, int size, String sort, String descrizione){
        if (size > 5) size = 5;
        return this.phylumRepository.findByDescrizioneContainingIgnoreCase(descrizione, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Phylum> findPhylumByStoria(int page, int size, String sort, String storia){
        if (size > 5) size = 5;
        return this.phylumRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

    //--------------------------------------------- Get Regno -----------------------------------------------------

    public Regno findRegnoByPhylumId(String id){
        Phylum found = this.phylumRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("not found"));
        return found.getRegno();
    }
}
