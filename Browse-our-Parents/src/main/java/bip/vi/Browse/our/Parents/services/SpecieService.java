package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.SpecieDTO;
import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.SpecieRepository;
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
import java.util.UUID;

@Service
public class SpecieService extends SetImg {
    @Autowired
    private SpecieRepository specieRepository;
    @Autowired
    private FenotipoService fenotipoService;
    @Autowired
    private GenereService genereService;
    @Autowired
    private  ItemService itemService;


    //----------------------------- Crud ---------------------------------------------------

    public Specie saveSpecie(SpecieDTO body) {
        Specie specie = new Specie(body, this.genereService.findGenereById(body.genere_id()));
        return specieRepository.save(specie);
    }

    public Page<Specie> findAllSpecie(int page, int size, String sort) {
        if (size > 100) size = 100;
        return this.specieRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Specie findSpecieById(String id) {
        return this.specieRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Specie " + id + " non trovata"));
    }

    public Specie findSpecieAndUpdate (String id, SpecieDTO body) {
        System.out.println(body.toString());
        Specie found = this.findSpecieById(id);
        found.setNome(body.nome_comune());
        found.setNome_comune(body.nome_comune());
        if (body.nome_scientifico() != null) found.setNome_scientifico(body.nome_scientifico());
        found.setDescrizione(body.descrizione());
        found.setStoria(body.storia());
        if (body.fenotipo_id() != null) found.setFenotipo(this.fenotipoService.findFenotipoById(body.fenotipo_id()));
        found.setGenere(this.genereService.findGenereById(body.genere_id()));
        System.out.println(found);
        return found;
    }

    public void deleteSpecie(String id) {
        Specie found = this.findSpecieById(id);
        this.specieRepository.delete(found);
        System.out.println("Specie " + found.getNome_comune() + " eliminata con successo");
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Specie found = this.findSpecieById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.specieRepository.save(found);
        return url;
    }

    //------------------------------------ Query ----------------------------------------------------

    public Page<Specie> findSpecoeByNome (String nome_comune, int page, int size, String sort) {
        if (size > 100) size = 100;
        return  this.specieRepository.findByNome_comuneContainingIgnoreCase(nome_comune, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Specie> findSpecieByDescrizione (String search, int page, int size, String sort) {
        if (size > 100) size = 100;
        return this.specieRepository.findByDescrizioneContainingIgnoreCase(search, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Specie> findSpecieByStoria (String storia, int page, int size, String sort) {
        if (size > 100) size = 100;
        return this.specieRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Specie findSpecieByFenotipoNome (String nomeFenotipo) {
        return this.fenotipoService.findFenotipiByNome(1,1, "nome", nomeFenotipo).getContent().get(1).getSpecie();
    }

    //-------------------------------------- Get Genere e Fenotipo ------------------------------------------

    public Genere findGenereBySpecieId (String id) {
        return this.findSpecieById(id).getGenere();
    }

    public Fenotipo findFenotipoBySpecieId (String id) {
        return this.findSpecieById(id).getFenotipo();
    }
}
