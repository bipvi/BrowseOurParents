package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.GenereDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GenereService {
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

    public Page<Genere> findAllGeneri(int page, int size, String sort) {
        if (size > 50) size = 50;
        return this.genereRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
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

    //---------------------------------- Query ------------------------------------------------------

    public Page<Genere> findGenereByNome(int page, int size, String sort, String nome) {
        if (size > 50) size = 50;
        return this.genereRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Genere> findGenereByDescrizione(int page, int size, String sort, String descrizione) {
        if (size > 50) size = 50;
        return this.genereRepository.findByDescrizioneContainingIgnoreCase(descrizione, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Genere> findGenereByStoria(int page, int size, String sort, String storia) {
        if (size > 50) size = 50;
        return this.genereRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

    //----------------------------------------- Get Famiglia --------------------------------------

    public Famiglia findFamigliaByGenereId(String id) {
        return  this.findGenereById(id).getFamiglia();
    }
}
