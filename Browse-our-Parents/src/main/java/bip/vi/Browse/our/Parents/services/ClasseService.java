package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.ClasseDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClasseService extends SetImg {
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private PhylumService phylumService;

    //----------------------------------- Crud -----------------------------------------
    public Classe saveClasse(ClasseDTO body){
        Phylum found = this.phylumService.findPhylumById(body.phylum_id());
        Classe classe = new Classe(body, found);
        return this.classeRepository.save(classe);
    }

    public Page<Classe> findAllClassi(int page, int size, String sort) {
        if (size > 50) size = 50;
        return this.classeRepository.findAll(PageRequest.of(page,size, Sort.by(sort)));
    }

    public List<Classe> findAll(){
        return this.classeRepository.findAll();
    }


    public Classe findClasseById(String id) {
        return this.classeRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("CLasse con id " + id + " non trovata"));
    }

    public void deleteClasseById(String id) {
        Classe found = this.findClasseById(id);
        this.classeRepository.delete(found);
        System.out.println("La classe " + found.getNome() + " è stata eliminata");
    }

    public Classe findClasseAndUpdate(String id, ClasseDTO body){
        Classe found = this.findClasseById(id);
        found.setDescrizione(body.descrizione());
        found.setNome(body.nome());
        found.setStoria(body.storia());
        Phylum f = this.phylumService.findPhylumById(body.phylum_id());
        found.setPhylum(f);
        return this.classeRepository.save(found);
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Classe found = this.findClasseById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.classeRepository.save(found);
        return url;
    }

    //------------------------------------- Query --------------------------------------------

    public Page<Classe> findClassiByName(int page, int size, String sort, String nome) {
        if (size > 50) size = 50;
        return this.classeRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page,size, Sort.by(sort)));
    }

    public Page<Classe> findClassiByDescrizione(int page, int size, String sort, String descrizione) {
        if (size > 50) size = 50;
        return this.classeRepository.findByDescrizioneContainingIgnoreCase(descrizione, PageRequest.of(page,size, Sort.by(sort)));
    }

    public Page<Classe> findClassiByStroia (int page, int size, String sort, String storia) {
        if (size > 50) size = 50;
        return this.classeRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page,size, Sort.by(sort)));
    }

    //------------------------------------ Get Phylum -----------------------------------------
     public Phylum getphylumByClasseId(String id) {
        return this.findClasseById(id).getPhylum();
     }

    public Classe findClaseRandom() {
        Random random = new Random();
        List<String> ids = this.findAll().stream().map((c) -> c.getId().toString()).toList();
        return this.findClasseById(ids.get(random.nextInt(ids.size())));
    }

    public List<Ordine> findOrdineByClasseId(String id) {
        return this.findClasseById(id).getOrdini();
    }
}
