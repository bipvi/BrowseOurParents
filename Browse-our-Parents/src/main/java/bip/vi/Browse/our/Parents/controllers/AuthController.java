package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.LoginDTO;
import bip.vi.Browse.our.Parents.DTO.NewUserDTO;
import bip.vi.Browse.our.Parents.DTO.UtenteLoginResponseDTO;
import bip.vi.Browse.our.Parents.entities.Genere;
import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.FamigliaService;
import bip.vi.Browse.our.Parents.services.SecurityService;
import bip.vi.Browse.our.Parents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private FamigliaService famigliaService;
    @Autowired
    private UserService userService;

    //---------------------------------- LOGIN --------------------------------------------------------------------
    @PostMapping("/login")
    public UtenteLoginResponseDTO login (@RequestBody @Validated LoginDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return new UtenteLoginResponseDTO(this.securityService.checkCredentialsAndGenerateToken(body));
    }

    //---------------------------------- SIGN-IN ------------------------------------------------------------------
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String message = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + message);
        }
        return this.userService.saveUser(body);
    }

    @GetMapping("/{Id}")
    public List<Genere> getGenres(@PathVariable String Id) {
        return this.famigliaService.getGeneriByFamigliaId(Id);
    }
}
