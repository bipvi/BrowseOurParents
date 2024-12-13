package bip.vi.Browse.our.Parents.entities;

import bip.vi.Browse.our.Parents.DTO.LoginDTO;
import bip.vi.Browse.our.Parents.DTO.NewUserDTO;
import bip.vi.Browse.our.Parents.entities.enums.Ruolo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"password","role","enabled","favourites","accountNonLocked","credentialsNonExpired","accountNonExpired","authorities"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private String avatar;

    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinTable(
            name = "favourite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "items_id")
    )
    private List<Item> favourites;

    public User(LoginDTO body){
        this.username = body.username();
        this.password = body.password();
        this.ruolo = Ruolo.USER;
    }

    public User(NewUserDTO body, PasswordEncoder bcrypt){
        this.username = body.username();
        this.password = bcrypt.encode(body.password());
        this.ruolo = Ruolo.USER;
        if (body.avatar() != null) {
            this.avatar = body.avatar();
        } else this.avatar = "https://robohash.org/" + username + ".png";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.ruolo = Ruolo.USER;
        this.avatar = "https://robohash.org/" + username + ".png";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ruolo.toString()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}
