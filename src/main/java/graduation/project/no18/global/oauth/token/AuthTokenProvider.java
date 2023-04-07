package graduation.project.no18.global.oauth.token;

import graduation.project.no18.global.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {
    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String seceet){
        this.key = Keys.hmacShaKeyFor(seceet.getBytes());
    }

    public AuthToken createAuthToken(String id, LocalDateTime expiry){
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(String id, String role, LocalDateTime expiry){
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken converteAuthToken(String token){
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken){

        if(authToken.validate()){

            Claims claims = authToken.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
        }else{
            throw new TokenValidFailedException();
        }
    }

}
