package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.ClasseDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Ordine;
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

import java.util.List;
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
    public List<Classe> findAllClassi() {
        return this.classeService.findAllClassi();
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
    public List<Classe> findClassiByNome(@RequestParam String nomeQuery){
        return this.classeService.findClassiByName(nomeQuery);
    }

    @GetMapping("/descQuery")
    public List<Classe> findClassiByDescrizione(@RequestParam String descQuery){
        return this.classeService.findClassiByDescrizione(descQuery);
    }

    @GetMapping("/storiaQuery")
    public List<Classe> findClassiByStroia(@RequestParam String storiaQuery){
        return this.classeService.findClassiByStroia(storiaQuery);
    }

    //--------------------------------- Get Phylum -----------------------------------------

    @GetMapping("/{classeID}/getPhylum")
    public Phylum getPhylumByClasseID (@PathVariable("classeID") String classeID){
        return this.classeService.getphylumByClasseId(classeID);
    }

    //--------------------------------- Get Ordini -----------------------------------------

    @GetMapping("/{classeID}/getOrdini")
    public List<Ordine> getOrdiniyClasseID (@PathVariable("classeID") String classeID){
        return this.classeService.findOrdineByClasseId(classeID);
    }

    @GetMapping("/getRandomly")
    public Classe getRandomly(){
        return this.classeService.findClaseRandom();
    }
}
