package bip.vi.Browse.our.Parents.config;


import bip.vi.Browse.our.Parents.entities.User;
import bip.vi.Browse.our.Parents.exceptions.UnauthorizedException;
import bip.vi.Browse.our.Parents.services.UserService;
import bip.vi.Browse.our.Parents.tools.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTCheckerFilter extends OncePerRequestFilter {
    @Autowired
    private JWT jwt;
    @Autowired
    private UserService userService;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Autorizzazione
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) throw new UnauthorizedException("Inserire token nell'Authorization Header nel formato corretto!");
        String token = authorizationHeader.substring(7);
        jwt.verifyToken(token);

        //Autenticazione
        String id = jwt.getIdFromToken(token);
        User user = this.userService.findUserById(Integer.parseInt(id));
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    };

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
