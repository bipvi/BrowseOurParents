package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.GenereDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.GenereService;
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
@RequestMapping("genere")
public class GenereController {
    @Autowired
    private GenereService genereService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{genereId}")
    public Genere getFamiglia(@PathVariable String genereId) {
        return this.genereService.findGenereById(genereId);
    }

    @GetMapping
    public Page<Genere> findAll(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "nome") String sortBy) {
        return this.genereService.findAllGeneri(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Genere create(@RequestBody @Validated GenereDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.genereService.saveGenere(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{genereId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Genere update(@PathVariable("genereId") String genereId, @RequestBody @Validated GenereDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.genereService.findAndUpdateGenere(genereId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{genereId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("genereId") String genereId) {
        this.genereService.findAndDeleteGenere(genereId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Genere> findByNome(@RequestParam String nomeQuery,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "nome") String sortBy){
        return this.genereService.findGenereByNome(page, size, sortBy, nomeQuery);
    }

    @GetMapping("/descQuery")
    public Page<Genere> findByDescrizione(@RequestParam String descQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.genereService.findGenereByDescrizione(page, size, sortBy , descQuery);
    }

    @GetMapping("/storiaQuery")
    public Page<Genere> findByStoria(@RequestParam String storiaQuery,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.genereService.findGenereByStoria(page, size, sortBy, storiaQuery);
    }

    //--------------------------------- Get Famiglia -----------------------------------------

    @GetMapping("/{genereId}/getFamiglia")
    public Famiglia getFamigliaBygenereId(@PathVariable("genereId") String genereId){
        return this.genereService.findFamigliaByGenereId(genereId);
    }
}
