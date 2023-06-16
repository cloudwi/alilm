package com.project.musinsastocknotificationbot.common.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final CustomUserDetailsService customUserDetailsService;
  private final String tokenSecretKey;
  private final String jwtTokenHeaderName;
  private static final long ACCESS_TOKEN_EXPIRED_TIME = Duration.ofMinutes(1).toMillis();

  public JwtTokenProvider(CustomUserDetailsService customUserDetailsService, @Value("${jwt.secret}") String tokenSecretKey,
      @Value("${jwt.header}") String jwtTokenHeaderName) {
    this.customUserDetailsService = customUserDetailsService;
    this.tokenSecretKey = tokenSecretKey;
    this.jwtTokenHeaderName = jwtTokenHeaderName;
  }

  public String createToken(long memberId) {
    Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));

    Date now = new Date();
    Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRED_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
        .compact();
  }

  public String removeBearer(String bearerToken) {
    return bearerToken.substring("Bearer ".length());
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(tokenSecretKey).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String getMemberId(String token) {
    return Jwts.parser()
        .setSigningKey(tokenSecretKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Authentication getAuthentication(String token) {
    String memberId = getMemberId(token);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberId);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }



  public String resolveToken(HttpServletRequest request) {
    String token = request.getHeader(jwtTokenHeaderName);

    if (token != null) {
      return removeBearer(token);
    } else {
      return null;
    }
  }
}
