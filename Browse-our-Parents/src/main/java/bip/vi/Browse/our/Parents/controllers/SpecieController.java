package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.SpecieDTO;
import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.ItemService;
import bip.vi.Browse.our.Parents.services.SpecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/specie")
public class SpecieController {
    @Autowired
    private SpecieService specieService;
    @Autowired
    private ItemService itemService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{specieId}")
    public Specie get(@PathVariable String specieId) {
        return this.specieService.findSpecieById(specieId);
    }

    @GetMapping
    public Page<Specie> findAll(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "nome") String sortBy) {
        return this.specieService.findAllSpecie(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Specie create(@RequestBody @Validated SpecieDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.specieService.saveSpecie(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{specieId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Specie update(@PathVariable("specieId") String specieId, @RequestBody @Validated SpecieDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.specieService.findSpecieAndUpdate(specieId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{specieId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("specieId") String specieId) {
        this.specieService.deleteSpecie(specieId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Specie> findByNome(@RequestParam String nomeQuery,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "nome") String sortBy){
        return this.specieService.findSpecoeByNome(nomeQuery, page, size, sortBy);
    }

    @GetMapping("/descQuery")
    public Page<Specie> findByDescrizione(@RequestParam String descQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.specieService.findSpecieByDescrizione(descQuery, page, size, sortBy);
    }

    @GetMapping("/storiaQuery")
    public Page<Specie> findByStoria(@RequestParam String storiaQuery,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.specieService.findSpecieByStoria(storiaQuery, page, size, sortBy);
    }

    @GetMapping("/fenotipoQuery")
    public Specie findByFenotipo(@RequestParam String fenotipoQuery){
        return this.specieService.findSpecieByFenotipoNome(fenotipoQuery);
    }

    //--------------------------------- Get Genere e Fenotipo -----------------------------------------

    @GetMapping("/{specieId}/getGenere")
    public Genere getGenereBySpecieId (@PathVariable("specieId") String specieId){
        return this.specieService.findGenereBySpecieId(specieId);
    }

    @GetMapping("/{specieId}/getFenotipo")
    public Fenotipo getFenotipoBySpecieId (@PathVariable("specieId") String specieId){
        return this.specieService.findFenotipoBySpecieId(specieId);
    }
}
