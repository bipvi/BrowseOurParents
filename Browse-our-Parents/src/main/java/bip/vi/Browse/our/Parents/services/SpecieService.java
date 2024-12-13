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
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public List<Specie> findAllSpecie() {
        return this.specieRepository.findAll();
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

    public List<Specie> findSpecoeByNome (String nome_comune) {
        return  this.specieRepository.findByNome_comuneContainingIgnoreCaseAndNome_scientificoContainsIgnoreCase(nome_comune, nome_comune);
    }

    public List<Specie> findSpecieByDescrizione (String search) {
        return this.specieRepository.findByDescrizioneContainingIgnoreCase(search);
    }

    public List<Specie> findSpecieByStoria (String storia) {
        return this.specieRepository.findByStoriaContainingIgnoreCase(storia);
    }

    public Specie findSpecieByFenotipoNome (String nomeFenotipo) {
        return this.fenotipoService.findFenotipiByNome( nomeFenotipo).get(1).getSpecie();
    }

    //-------------------------------------- Get Genere e Fenotipo ------------------------------------------

    public Genere findGenereBySpecieId (String id) {
        return this.findSpecieById(id).getGenere();
    }

    public Fenotipo findFenotipoBySpecieId (String id) {
        return this.findSpecieById(id).getFenotipo();
    }

    public Specie getRandomSpecie() {
        List<Specie> species = this.specieRepository.findAll();
        Random random = new Random();
        return species.get(random.nextInt(species.size()));
    }
}
