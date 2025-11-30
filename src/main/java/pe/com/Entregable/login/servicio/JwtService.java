package pe.com.Entregable.login.servicio;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pe.com.Entregable.login.seguridad.UsuarioDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_KEY_STRING = "dGhpcyBpcyBhIHZlcnkgc2VjdXJlIGFuZCBsb25nIGtleSBmb3IgbXkgYm9saXZhciBhcHA=";

    public String generateToken(UsuarioDetails usuarioDetails) {

        List<String> roles = usuarioDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String nombreCompleto = usuarioDetails.getNombreCompleto();

        return Jwts.builder()
                .setSubject(usuarioDetails.getUsername())
                .claim("roles", roles)
                .claim("nombre", nombreCompleto)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Usa el método getSigningKey
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_STRING);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}