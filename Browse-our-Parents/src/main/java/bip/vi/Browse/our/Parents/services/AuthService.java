package bip.vi.Browse.our.Parents.services;


import bip.vi.Browse.our.Parents.DTO.LoginDTO;
import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.exceptions.UnauthorizedException;
import bip.vi.Browse.our.Parents.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWT jwt;

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        User found = this.userService.findUserByUsername(body.username());
        System.out.println(found);
        if(bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.generateToken(found);
        } else throw new UnauthorizedException("Credenziali errate");
    }
}
