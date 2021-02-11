package com.project.member.util;
import com.project.member.domain.Member;
import com.project.member.domain.MemberRole;
import com.project.member.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 10L;//재발급때문에 잠깐 10초
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private Key key;


    @PostConstruct
    private void initKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        this.key =  Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {

        Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        logger.info("test extractAllClaims" + claims.toString());

        return claims;

    }

    public String getMemberRole(String token) {
        String role = extractAllClaims(token).get("role",String.class);//?
        logger.info("jwt getMemberRole  : " + role);
        return role;
    }

    public Long getMemberId(String token) {

        Long memberId = extractAllClaims(token).get("memberId",Long.class);//?
        logger.info("jwt getMemberId : " + memberId);
        return memberId;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Boolean isTokenExpired(String token) {

        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            logger.info("만료되지 않은 JWT 토큰입니다.");
            return false;
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
            return true;
        }
    }

    public String generateToken(Member member) {
        return doGenerateToken(member.getId(),member.getRole().toString(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Member member) {
        return doGenerateToken(member.getId(), member.getRole().toString(), REFRESH_TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(Long memberId, String role, long expireTime) {

       Claims claims = Jwts.claims();
        claims.put("role", role);
        claims.put("memberId",memberId);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //logger.info("test" + getMemberId(jwt));//?
        //logger.info("test role" + getMemberRole(jwt));//?
        return jwt;
    }

}