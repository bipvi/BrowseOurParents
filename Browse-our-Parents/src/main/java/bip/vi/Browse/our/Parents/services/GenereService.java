package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.GenereDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class GenereService extends SetImg {
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private FamigliaService famigliaService;

    //--------------------------------- Crud ----------------------------------------------

    public Genere saveGenere(GenereDTO body) {
        Genere genere = new Genere(body, this.famigliaService.findFamigliaById(body.famiglia_id()));
        return this.genereRepository.save(genere);
    }

    public Genere findGenereById(String id) {
        return this.genereRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Genere con id: " + id + " non trovato"));
    }

    public List<Genere> findAllGeneri() {
        return this.genereRepository.findAll();
    }

    public Genere findAndUpdateGenere(String id, GenereDTO body) {
        Genere found = this.findGenereById(id);
        found.setStoria(body.storia());
        found.setDescrizione(body.descrizione());
        found.setFamiglia(this.famigliaService.findFamigliaById(body.famiglia_id()));
        found.setNome(body.nome());
        return this.genereRepository.save(found);
    }

    public void findAndDeleteGenere(String id) {
        Genere found = this.findGenereById(id);
        this.genereRepository.delete(found);
        System.out.println("Il genere "+ found.getNome() + "eliminato con successo");
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Genere found = this.findGenereById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.genereRepository.save(found);
        return url;
    }

    //---------------------------------- Query ------------------------------------------------------

    public List<Genere> findGenereByNome( String nome) {
        return this.genereRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Genere> findGenereByDescrizione(String descrizione) {
        return this.genereRepository.findByDescrizioneContainingIgnoreCase(descrizione);
    }

    public List<Genere> findGenereByStoria( String storia) {
        return this.genereRepository.findByStoriaContainingIgnoreCase(storia);
    }

    //----------------------------------------- Get Famiglia --------------------------------------

    public Famiglia findFamigliaByGenereId(String id) {
        return  this.findGenereById(id).getFamiglia();
    }

    public List<Specie> findSpecieByGenereId(String id) { return  this.findGenereById(id).getLista_specie(); }
}
