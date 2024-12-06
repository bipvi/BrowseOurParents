package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.RegnoDTO;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.entities.Regno;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.RegnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class RegnoService extends SetImg {
    @Autowired
    private RegnoRepository regnoRepository;

    //---------------------------- Crud ---------------------------------

    public Regno saveRegno(RegnoDTO body) {
        Regno regno = new Regno(body);
        return this.regnoRepository.save(regno);
    }

    public Page<Regno> findAllRegni(int page, int size, String sort) {
        if (size > 10) size = 10;
        return this.regnoRepository.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    public Regno findRegnoById(String id) {
        return this.regnoRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Il regno con id: " + id + " non esiste"));
    }

    public Regno findRegnoAndUpdate(RegnoDTO body, String id) {
        Regno found = this.findRegnoById(id);
        found.setStoria(body.storia());
        found.setDescrizione(body.descrizione());
        found.setNome(body.nome());
        return this.regnoRepository.save(found);
    }

    public void deleteRegno(String id) {
        Regno found = this.findRegnoById(id);
        this.regnoRepository.delete(found);
        System.out.println("Il regno: " + found.getNome() + " è stato cancellato con successo");
    }

    //----------------------------------- Set Img ---------------------------------------------------
    public String setImg(String id, MultipartFile file) {
        Regno found = this.findRegnoById(id);
        String url = this.getUrl(file);
        found.setImg(url);
        this.regnoRepository.save(found);
        return url;
    }

    //------------------------------ Query ------------------------------------------------

    public Page<Regno> findRegniByNome(int page, int size, String sort, String nome) {
        if (size > 10) size = 10;
        return this.regnoRepository.findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Regno> findRegniByDescrzione(int page, int size, String sort, String descrizione) {
        if (size > 10) size = 10;
        return this.regnoRepository.findByDescrizioneContainingIgnoreCase(descrizione, PageRequest.of(page, size, Sort.by(sort)));
    }

    public Page<Regno> findRegniBystoria(int page, int size, String sort, String storia) {
        if (size > 10) size = 10;
        return this.regnoRepository.findByStoriaContainingIgnoreCase(storia, PageRequest.of(page, size, Sort.by(sort)));
    }

    public List<Phylum> findPhylumsByRegnoId(String id) {
        return this.findRegnoById(id).getPhylums();
    }
}
