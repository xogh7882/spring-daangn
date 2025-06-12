package com.ssafy.Daangn.Jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider implements InitializingBean {
    private static final String AUTHORITIES_KEY = "auth";
    private static final long expirationTime = 1000 * 60 * 30; // 30분
    private static final long RefreshTokenExpirationTime = 1000 * 60 * 60 * 24 * 7;  // 7일 ( refresh는 길게 )

    private final String secret;
    private final UserDetailsService userDetailsService;
    private Key key;


    public TokenProvider(@Value("${jwt.secret}") String secret, UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // JWT Token 을 만들거야
    public String createAccessToken(Integer id, Authentication authentication) {
        // 사용자 권한 목록을 가져오자 ( authorities = "ROLE_USER" )
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Token 만료 시간 설정 ( expirationTime = 30분으로 설정 )
        long now = (new Date().getTime());
        Date tokenExpiresIn = new Date(now + expirationTime);

        // JJWT 라이브러리의 JWT 생성 빌더 패턴 사용
        return Jwts.builder()
                .setSubject(id.toString())           // subject : JWT 토큰이 누구인지 식별
                .claim(AUTHORITIES_KEY, authorities) // claim : 사용자 권한 정보
                .setExpiration(tokenExpiresIn)       // expiration : 만료 시간
                .signWith(key, SignatureAlgorithm.HS512)  // signwith : 토큰 서명 ( 비밀키 )
                .compact(); // 최종 JWT 문자열 생성
    }

    public String getTokenUserId(String token){
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch(Exception e){
            return null;
        }
    }

    public Authentication getAuthentication(String token) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } catch(Exception e){
            return null;
        }
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // Refresh Token 은 랜덤 문자열로 생성한다 ( 어차피 새로 발급할때만 쓸거라서 )
    public String createRefreshToken() {
        byte[] array = new byte[64];
        new SecureRandom().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    public long getAccessTokenExpirationTime() {
        return expirationTime;
    }

    public long getRefreshTokenExpirationTime() {
        return RefreshTokenExpirationTime;
    }

    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if(refreshToken != null && !refreshToken.isEmpty()){
            return refreshToken;
        }
        return null;
    }

    // RefreshToken 유효성 검증 ( 문자열 길이와 형식만 확인 )
    public boolean validateRefreshToken(String refreshToken) {
        try{
            byte[] decode = Base64.getDecoder().decode(refreshToken);
            return decode.length == 64;
        } catch(Exception e){
            return false;
        }
    }

}
