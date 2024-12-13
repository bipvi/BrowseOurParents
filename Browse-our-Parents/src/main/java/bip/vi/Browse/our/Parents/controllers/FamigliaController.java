package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.FamigliaDTO;
import bip.vi.Browse.our.Parents.entities.Famiglia;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.Ordine;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.FamigliaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/famiglia")
public class FamigliaController {
    @Autowired
    private FamigliaService famigliaService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{famigliaId}")
    public Famiglia getFamiglia(@PathVariable String famigliaId) {
        return this.famigliaService.findFamigliaById(famigliaId);
    }

    @GetMapping
    public List<Famiglia> findAllFamiglie() {
        return this.famigliaService.findAllFamiglie();
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Famiglia createFamiglia(@RequestBody @Validated FamigliaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.famigliaService.saveFamiglia(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{famigliaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Famiglia updateFamiglia(@PathVariable("famigliaId") String famigliaId, @RequestBody @Validated FamigliaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.famigliaService.findAndUpdateFamiglia(body, famigliaId);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{famigliaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFamiglia(@PathVariable("famigliaId") String famigliaId) {
        this.famigliaService.findAndDeleteFamiglia(famigliaId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public List<Famiglia> findFamiglieByNome(@RequestParam String nomeQuery){
        return this.famigliaService.findFamiglieByNome(nomeQuery);
    }

    @GetMapping("/descQuery")
    public List<Famiglia> findFamiglieByDescrizione(@RequestParam String descQuery){
        return this.famigliaService.findFamiglieByDescrizione(descQuery);
    }

    @GetMapping("/storiaQuery")
    public List<Famiglia> findFamiglieByStoria(@RequestParam String storiaQuery){
        return this.famigliaService.findFamiglieByStoria(storiaQuery);
    }

    //--------------------------------- Get Ordine -----------------------------------------

    @GetMapping("/{famigliaId}/getOrdine")
    public Ordine getPhylumByFamigliaID (@PathVariable("famigliaId") String famigliaId){
        return this.famigliaService.getOrdineByFamigliaId(famigliaId);
    }

    //--------------------------------- Get Generi -----------------------------------------

    @GetMapping("/{famigliaId}/getGeneri")
    public List<Genere> getGeneriByFamigliaID (@PathVariable("famigliaId") String famigliaId){
        return this.famigliaService.getGeneriByFamigliaId(famigliaId);
    }
}
