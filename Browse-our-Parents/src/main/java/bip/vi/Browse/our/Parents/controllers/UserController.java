package bip.vi.Browse.our.Parents.controllers;

import bip.vi.Browse.our.Parents.DTO.PasswordDTO;
import bip.vi.Browse.our.Parents.DTO.UpdateUserDTO;
import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // --------------------------- GET -------------------------------------------------

    @GetMapping
    public List<User> getUsers() {
        return this.userService.findAllUsers();
    }

    //-------userId----------->
    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return this.userService.findUserById(userId);
    }

    //------ME---------->
    @GetMapping("/me")
    public User getMyUser(@AuthenticationPrincipal User user) {
        return user;
    }

    // ------------------------------ PUT ----------------------------------------------

    // ------- Only Admin and moderator --------------------
    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public User updateUser(@PathVariable("userId") int userId, @Validated @RequestBody UpdateUserDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.findUserAndUpdate(userId, body);
    }

    // ----------------------------- DELETE ---------------------------------------------

    //------ME---------->
    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyUser(@AuthenticationPrincipal User user) {
        this.userService.findUserAndDelete(user.getId());
    }

    //-------userId----------->
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int userId) {
        this.userService.findUserAndDelete(userId);
    }

    // ----------------------------- PATCH -----------------------------------------------

    //------ME---------->
    @PatchMapping("/me/avatar")
    public String uploadAvatar(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestParam("avatar") MultipartFile file, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserAvatar(currentAuthenticatedUser.getId(), file);
    }

    //-------userId----------->
    @PatchMapping("/{userId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public String findAndUploadAvatr(@PathVariable("userId") int userId, @RequestParam("avatar") MultipartFile file, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserAvatar(userId, file);
    }

    //-----------ME---------->
    @PatchMapping("/me/url")
    public String uploadUrlAvatar(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestParam("url") String url, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserAvatar(currentAuthenticatedUser.getId(), url);
    }

    //--------userId---------->
    @PatchMapping("/{userId}/url")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public String findAndUploadAvatr(@PathVariable("userId") int userId, @RequestParam("url") String url, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserAvatar(userId, url);
    }

    // ----------- Ruolo --------------------------

    // ----------- ME -------------->
    @PatchMapping("/me/ruolo")
    public User updateRuolo(@AuthenticationPrincipal User user, @RequestParam("ruolo" ) String ruolo, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserRuolo(user.getId(), ruolo);
    }

    // -------- userId ------------>
    @PatchMapping("/{userId}/ruolo")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public User updateRuolo(@PathVariable("userId") int userId, @RequestParam("ruolo" ) String ruolo, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.userService.setUserRuolo(userId, ruolo);
    }

    // ---------------- Password ----------------------

    // ----------- ME -------------->
    @PatchMapping("/me/password")
    public void updatePassword(@AuthenticationPrincipal User user, @RequestParam("password" ) @Validated PasswordDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        this.userService.setUserPassword(user.getId(), body.password());
    }

    // -------- userId ------------>
    @PatchMapping("/{userId}/password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public void updatePassword(@PathVariable("userId") int userId, @RequestParam("password" ) String password, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        this.userService.setUserPassword(userId, password);
    }
}
