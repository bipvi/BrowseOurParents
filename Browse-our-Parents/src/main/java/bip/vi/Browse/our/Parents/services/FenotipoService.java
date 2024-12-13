package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.FenotipoDTO;
import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.FenotipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FenotipoService {
    @Autowired
    private FenotipoRepository fenotipoRepository;

    //---------------------------------- Crud --------------------------------------------------------

    public Fenotipo saveFenotipo (FenotipoDTO body){
        Fenotipo fenotipo = new Fenotipo(body);
        return this.fenotipoRepository.save(fenotipo);
    }

    public Page<Fenotipo> findAllFenotipi (int size, int page, String sort){
        if (size > 50) size = 50;
        return this.fenotipoRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Fenotipo findFenotipoById(String id){
        return this.fenotipoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Fenotipo con id: " + id + " non trovato "));
    }

    public Fenotipo findFenotipoAndUpdate(String id, FenotipoDTO body){
        Fenotipo found = this.findFenotipoById(id);
        found.setImg(body.img());
        found.setNome(body.nome());
        return this.fenotipoRepository.save(found);
    }

    public void findAndDeleteFenotipo(String id){
        Fenotipo found = this.findFenotipoById(id);
        this.fenotipoRepository.delete(found);
        System.out.println("Fenotipo " + found.getNome() + " eliminato con successo");
    }

    //---------------------------------- Query ------------------------------------------------------

    public List<Fenotipo> findFenotipiByNome ( String nome){
        return this.fenotipoRepository.findByNomeContainingIgnoreCase(nome);
    }

    //-------------------------------------- Get Specie ------------------------------------------

    public Specie getSpecieByFenotipoId (String id){
        return this.findFenotipoById(id).getSpecie();
    }
}
