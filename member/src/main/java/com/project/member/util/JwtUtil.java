package com.project.member.util;
import com.project.member.domain.Member;
import com.project.member.domain.MemberRole;
import com.project.member.service.MemberService;
import io.jsonwebtoken.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.Null;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 10;
    public final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 24 * 2;

    final static public String ACCESS_TOKEN_NAME = "accessToken";
    final static public String REFRESH_TOKEN_NAME = "refreshToken";

    private final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private Key key;


    @PostConstruct
    private void initKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        this.key =  new SecretKeySpec(apiKeySecretBytes,SignatureAlgorithm.HS256.getJcaName());
    }

    private  Claims getClaimsFormToken(String token) {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }


    public String getMemberRole(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("role");
    }

    public String getMemberId(String token) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get("memberId");
    }


    public boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            return true;
        } catch ( MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch (NullPointerException e) {
            logger.info("JWT 토큰이 빈 값입니다.");
        }
        return false;
    }

    public Boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFormToken(token);
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
        return Jwts.builder()
                .setClaims(createClaims(memberId,role))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private Map<String,Object> createClaims(Long memberId, String role) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("memberId",memberId.toString());
        claims.put("role",role);
        return claims;
    }

}