package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.PhylumDTO;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.entities.Regno;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.PhylumService;
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
@RequestMapping("/phylum")
public class PhylumController {
    @Autowired
    private PhylumService phylumService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{phylumId}")
    public Phylum getPhylum(@PathVariable("phylumId") String phylumId) {
        return this.phylumService.findPhylumById(phylumId);
    }

    @GetMapping
    public Page<Phylum> findAll(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size,
                          @RequestParam(defaultValue = "nome") String sortBy) {
        return this.phylumService.findAllPhylum(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Phylum create(@RequestBody @Validated PhylumDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.phylumService.save(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{phylumId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Phylum update(@PathVariable("phylumId") String phylumId, @RequestBody @Validated PhylumDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.phylumService.findPhylumAndUpdate(phylumId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{phylumId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("phylumId") String phylumId) {
        this.phylumService.findPhylumAndDelete(phylumId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Phylum> findByNome(@RequestParam String nomeQuery,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "nome") String sortBy){
        return this.phylumService.findPhylumQueryNome(page, size, sortBy, nomeQuery);
    }

    @GetMapping("/descQuery")
    public Page<Phylum> findByDescrizione(@RequestParam String descQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.phylumService.findPhylumByDescrizione(page, size, sortBy, descQuery );
    }

    @GetMapping("/storiaQuery")
    public Page<Phylum> findByStoria(@RequestParam String storiaQuery,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.phylumService.findPhylumByStoria(page, size, sortBy, storiaQuery);
    }

    //--------------------------------- Get Regno -----------------------------------------

    @GetMapping("/{phylumId}/getRegno")
    public Regno getRegnoByPhylumId (@PathVariable("phylumId") String phylumId){
        return this.phylumService.findRegnoByPhylumId(phylumId);
    }
}
