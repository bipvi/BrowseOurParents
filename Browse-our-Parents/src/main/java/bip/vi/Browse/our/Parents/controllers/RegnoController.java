package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.RegnoDTO;
import bip.vi.Browse.our.Parents.entities.Phylum;
import bip.vi.Browse.our.Parents.entities.Regno;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.RegnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/regno")
public class RegnoController {
    @Autowired
    private RegnoService regnoService;

    // ------------------------------ Crud ------------------------------------

    // ----------------- GET ------------------------
    @GetMapping("/{regnoId}")
    public Regno get(@PathVariable("regnoId") String regnoId) {
        return this.regnoService.findRegnoById(regnoId);
    }

    @GetMapping
    public Page<Regno> findAll(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "nome") String sortBy) {
        return this.regnoService.findAllRegni(page, size, sortBy);
    }

    // --------------------- POST ---------------------
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Regno create(@RequestBody @Validated RegnoDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.regnoService.saveRegno(body);
    }

    // --------------------- PUT ------------------------
    @PutMapping("/{regnoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Regno update(@PathVariable("regnoId") String regnoId, @RequestBody @Validated RegnoDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.regnoService.findRegnoAndUpdate(body, regnoId);
    }

    // -------------------- DELETE --------------------------
    @DeleteMapping("/{regnoId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("regnoId") String regnoId) {
        this.regnoService.deleteRegno(regnoId);
    }

    // ------------------ PATCH -----------------------------
    @PatchMapping("/{regnoId}/img")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String setImg(@PathVariable("regnoId") String regnoId, @RequestParam("img")MultipartFile img){
        return this.regnoService.setImg(regnoId, img);
    }
    //---------------------------------------- QUERY -----------------------------------

    @GetMapping("/nomeQuery")
    public Page<Regno> findByNome(@RequestParam String nomeQuery,
                             @RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(defaultValue = "nome") String sortBy){
        return this.regnoService.findRegniByNome(page, size, sortBy, nomeQuery);
    }

    @GetMapping("/descQuery")
    public Page<Regno> findByDescrizione(@RequestParam String descQuery,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.regnoService.findRegniByDescrzione(page, size, sortBy, descQuery);
    }

    @GetMapping("/storiaQuery")
    public Page<Regno> findByStoria(@RequestParam String storiaQuery,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(defaultValue = "descrizione") String sortBy){
        return this.regnoService.findRegniBystoria(page, size, sortBy, storiaQuery);
    }

    //--------------------------------- Get Phylums -----------------------------------------

    @GetMapping("/{regnoId}/getPhylums")
    public List<Phylum> getPhylumsByRegnoId (@PathVariable("regnoId") String regnoId){
        return this.regnoService.findPhylumsByRegnoId(regnoId);
    }
}
