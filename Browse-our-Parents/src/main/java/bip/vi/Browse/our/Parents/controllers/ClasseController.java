package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.ClasseDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.ClasseService;
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
@RequestMapping("/classe")
public class ClasseController {
    @Autowired
    private ClasseService classeService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{classeId}")
    public Classe getClasse(@PathVariable String classeId) {
        return this.classeService.findClasseById(classeId);
    }

    @GetMapping
    public Page<Classe> findAllClassi(@RequestParam(defaultValue = "1") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "nome") String sortBy) {
        return this.classeService.findAllClassi(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Classe createClasse(@RequestBody @Validated ClasseDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.classeService.saveClasse(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{classeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Classe updateClasse(@PathVariable("classeId") String classeId, @RequestBody @Validated ClasseDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.classeService.findClasseAndUpdate(classeId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{classeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClasse(@PathVariable("classeId") String classeId) {
        this.classeService.deleteClasseById(classeId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Classe> findClassiByNome(@RequestParam String nomeQuery,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "nome") String sortBy){
        return this.classeService.findClassiByName(page, size, sortBy ,nomeQuery);
    }

    @GetMapping("/descQuery")
    public Page<Classe> findClassiByDescrizione(@RequestParam String descQuery,
                                         @RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.classeService.findClassiByDescrizione(page, size, sortBy ,descQuery);
    }

    @GetMapping("/storiaQuery")
    public Page<Classe> findClassiByStroia(@RequestParam String storiaQuery,
                                                @RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.classeService.findClassiByStroia(page, size, sortBy ,storiaQuery);
    }

    //--------------------------------- Get Phylum -----------------------------------------

    @GetMapping("/{classeID}/getPhylum")
    public Phylum getPhylumByClasseID (@PathVariable("classeID") String classeID){
        return this.classeService.getphylumByClasseId(classeID);
    }
}
