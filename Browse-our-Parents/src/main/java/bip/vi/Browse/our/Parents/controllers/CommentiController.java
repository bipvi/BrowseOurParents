package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.CommentoDTO;
import bip.vi.Browse.our.Parents.entities.Commento;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.CommentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/commenti")
public class CommentiController {
    @Autowired
    private CommentoService commentoService;

    // ------------ POST ---------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Commento saveCommento(@RequestBody CommentoDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) throw new BadRequestException("Ci sono stati errori nel payload! " + validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". ")));
        return this.commentoService.save(body);
    }

    //--------------- GET ------------------
    @GetMapping
    public List<Commento> getAllCommenti() {
        return this.commentoService.findAll();
    }

    @GetMapping("/{commentoId}")
    public Commento getCommento(@PathVariable("commentoId") int commentoId) {
        return this.commentoService.findById(commentoId);
    }

    @GetMapping("/{itemId}/getComments")
    public List<Commento> getCommentsByIemId(@PathVariable("itemId") String itemId) {
        return this.commentoService.findByItem(itemId);
    }

    // ------------------- DELETE --------------
    @DeleteMapping("/{commentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommento(@PathVariable("commentoId") int commentoId) {
        this.commentoService.deleteCommento(commentoId);
    }

    // ------------------- PATCH ----------------
    @PatchMapping("/{commentoId}/contenuto")
    public Commento updateContenuto(@PathVariable("commentoId") int commentoId, @RequestParam("contenuto") String contenuto) {
        return this.commentoService.updateContenuto(contenuto, commentoId);
    }
}
