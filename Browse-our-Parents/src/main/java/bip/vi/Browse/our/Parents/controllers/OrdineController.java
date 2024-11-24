package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.OrdineDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.OrdineService;
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
@RequestMapping("/ordine")
public class OrdineController {
    @Autowired
    private OrdineService ordineService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{ordineId}")
    public Ordine getOrdineById(@PathVariable String ordineId) {
        return this.ordineService.findOrdineById(ordineId);
    }

    @GetMapping
    public Page<Ordine> findAll(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "nome") String sortBy) {
        return this.ordineService.findAllOrdini(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Ordine create(@RequestBody @Validated OrdineDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.ordineService.saveOrdine(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{ordineId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Ordine update(@PathVariable("ordineId") String ordineId, @RequestBody @Validated OrdineDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.ordineService.findOrdineAndUpdate(ordineId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{ordineId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("ordineId") String ordineId) {
        this.ordineService.findOrdineAndDelete(ordineId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Ordine> findByNome(@RequestParam String nomeQuery,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "nome") String sortBy){
        return this.ordineService.findOrdiniByNome( page, size, sortBy, nomeQuery);
    }

    @GetMapping("/descQuery")
    public Page<Ordine> findByDescrizione(@RequestParam String descQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.ordineService.findOrdiniByDescrizione(page, size, sortBy, descQuery );
    }

    @GetMapping("/storiaQuery")
    public Page<Ordine> findByStoria(@RequestParam String storiaQuery,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.ordineService.findOrdiniByStoria(page, size, sortBy, storiaQuery );
    }

    //--------------------------------- Get Classe -----------------------------------------

    @GetMapping("/{ordineId}/getClasse")
    public Classe getClasseByordineId (@PathVariable("ordineId") String ordineId){
        return this.ordineService.getClasseByOrdineId(ordineId);
    }
}
