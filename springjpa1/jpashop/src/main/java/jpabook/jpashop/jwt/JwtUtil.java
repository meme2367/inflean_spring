package jpabook.jpashop.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jpabook.jpashop.domain.member.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${jwt.secret}")
    private String SECRET;

    public Key getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsername(String token) {
        return extractAllClaims(token)
                .get("username", String.class);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token)
                .getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(Member member,Authentication authentication) {
        return doGenerateToken(member.getUsername(), TOKEN_VALIDATION_SECOND,authentication);
    }

    public String generateRefreshToken(Member member,Authentication authentication) {
        return doGenerateToken(member.getUsername(), REFRESH_TOKEN_VALIDATION_SECOND,authentication);
    }

    public String doGenerateToken(String username, long expireTime,Authentication authentication) {


        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));


        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put(AUTHORITIES_KEY,authorities);


        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET), SignatureAlgorithm.HS512)
                .compact();

        return jwt;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Authentication getAuthentication(String token,UserDetails userDetails) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey(SECRET))
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());




        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }
}
