package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.FenotipoDTO;
import bip.vi.Browse.our.Parents.entities.Fenotipo;
import bip.vi.Browse.our.Parents.entities.Specie;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.FenotipoService;
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
@RequestMapping("/fenotipo")
public class FenotipoController {
    @Autowired
    FenotipoService fenotipoService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{fenotipoId}")
    public Fenotipo getFenotipo(@PathVariable String fenotipoId) {
        return this.fenotipoService.findFenotipoById(fenotipoId);
    }

    @GetMapping
    public Page<Fenotipo> findAllClassi(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "nome") String sortBy) {
        return this.fenotipoService.findAllFenotipi(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Fenotipo createFenotipo(@RequestBody @Validated FenotipoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.fenotipoService.saveFenotipo(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{fenotipoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Fenotipo updateFenotipo(@PathVariable("fenotipoId") String fenotipoId, @RequestBody @Validated FenotipoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.fenotipoService.findFenotipoAndUpdate(fenotipoId, body);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{fenotipoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFenotipo(@PathVariable("fenotipoId") String fenotipoId) {
        this.fenotipoService.findAndDeleteFenotipo(fenotipoId);
    }

    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/{nomeQuery}")
    public List<Fenotipo> findFFenotipiByNome(@PathVariable("nomeQuery") String nomeQuery){
        return this.fenotipoService.findFenotipiByNome( nomeQuery);
    }

    //--------------------------------- Get Specie -----------------------------------------

    @GetMapping("/{fenotipoId}getSpecie")
    public Specie getSpecieByFenotipoID (@PathVariable("fenotipoId") String fenotipoId){
        return this.fenotipoService.getSpecieByFenotipoId(fenotipoId);
    }
}
