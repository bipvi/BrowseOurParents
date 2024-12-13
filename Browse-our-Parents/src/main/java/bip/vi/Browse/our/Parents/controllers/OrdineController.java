package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.OrdineDTO;
import bip.vi.Browse.our.Parents.entities.Classe;
import bip.vi.Browse.our.Parents.entities.Famiglia;
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

import java.util.List;
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
    public List<Ordine> findAll() {
        return this.ordineService.findAllOrdini();
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
    public List<Ordine> findByNome(@RequestParam String nomeQuery){
        return this.ordineService.findOrdiniByNome( nomeQuery);
    }

    @GetMapping("/descQuery")
    public List<Ordine> findByDescrizione(@RequestParam String descQuery){
        return this.ordineService.findOrdiniByDescrizione(descQuery );
    }

    @GetMapping("/storiaQuery")
    public List<Ordine> findByStoria(@RequestParam String storiaQuery){
        return this.ordineService.findOrdiniByStoria(storiaQuery );
    }

    //--------------------------------- Get Classe -----------------------------------------

    @GetMapping("/{ordineId}/getClasse")
    public Classe getClasseByordineId (@PathVariable("ordineId") String ordineId){
        return this.ordineService.getClasseByOrdineId(ordineId);
    }

    //--------------------------------- Get Famiglie -----------------------------------------

    @GetMapping("/{ordineId}/getFamiglie")
    public List<Famiglia> getFamiglieByordineId (@PathVariable("ordineId") String ordineId){
        return this.ordineService.findFamigliaByOrdineId(ordineId);
    }
}
