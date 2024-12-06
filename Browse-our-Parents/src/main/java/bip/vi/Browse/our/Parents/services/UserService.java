package bip.vi.Browse.our.Parents.services;

import bip.vi.Browse.our.Parents.DTO.NewUserDTO;
import bip.vi.Browse.our.Parents.DTO.UpdateUserDTO;
import bip.vi.Browse.our.Parents.entities.Item;
import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.entities.enums.Ruolo;
import bip.vi.Browse.our.Parents.exceptions.BadRequestException;
import bip.vi.Browse.our.Parents.exceptions.NotFoundException;
import bip.vi.Browse.our.Parents.repo.UserRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ItemService itemService;

    //------------------------------ Crud -------------------------------

    public User saveUser(NewUserDTO body) {
        if (this.findUserByUsername(body.username()) == null) {
            User user = new User(body, bcrypt);
            return this.userRepository.save(user);
        } else throw new BadRequestException("Username " + body.username() + "already exists");
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    public User findUserById(int id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("L'utente con id: " + id + " non trovato"));
    }

    //------ Only ADMIN and MODERATOR ------------------------
    public User findUserAndUpdate(int id, UpdateUserDTO body) {
        User found = this.findUserById(id);
        // ---- accetta l'url dell'immagine ----
        found.setAvatar(body.avatar());
        found.setPassword(bcrypt.encode(body.password()));
        found.setUsername(body.username());
        found.setRuolo(body.ruolo());
        return this.userRepository.save(found);
    }

    public void findUserAndDelete(int id) {
        User found = this.findUserById(id);
        this.userRepository.delete(found);
    }

    //------------------------ Query -----------------------------------------------------------

    public User findUserByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    //-------------------------- Set Password, Ruolo, Username e Avatar -----------------------------------------

    public void setUserPassword(int id, String password) {
        User found = this.findUserById(id);
        found.setPassword(bcrypt.encode(password));
        this.userRepository.save(found);
    }

    public String setUserUsername(int id, String username) {
        User found = this.findUserById(id);
        if (this.findUserByUsername(username) == null) {
            found.setUsername(username);
            this.userRepository.save(found);
        } else throw new BadRequestException("Username " + username + " esiste già");
        return found.getUsername();
    }

    // ------------------ Tramite file ------------
    public String setUserAvatar(int id, MultipartFile file) {
        String url = null;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        User user = this.findUserById(id);
        user.setAvatar(url);
        this.userRepository.save(user);

        return url;
    }

    // ------------------ Tramite url ---------------
    public String setUserAvatar(int id, String url) {
        User user = this.findUserById(id);
        user.setAvatar(url);
        this.userRepository.save(user);
        return url;
    }

    public User setUserRuolo(int id, String ruolo) {
        User found = this.findUserById(id);
        found.setRuolo(Ruolo.valueOf(ruolo.toUpperCase()));
        return this.userRepository.save(found);
    }

    // ---------------------- Favourite --------------------
    public List<Item> getUserFavourites(int id) {
        User found = this.findUserById(id);
        return found.getFavourites();
    }

    public Item addFavourites (int id, String uuid) {
        User found = this.findUserById(id);
        List<Item> fav = found.getFavourites();
        fav.add(this.itemService.findById(uuid));
        found.setFavourites(fav);
        this.userRepository.save(found);
        return this.itemService.findById(uuid);
    }

    public void removeFavourites(int id, String uuid) {
        User found = this.findUserById(id);
        List<Item> fav = found.getFavourites();
        fav.remove(this.itemService.findById(uuid));
        found.setFavourites(fav);
    }
}
