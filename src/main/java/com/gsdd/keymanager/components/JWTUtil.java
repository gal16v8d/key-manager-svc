package com.gsdd.keymanager.components;

import java.time.Instant;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.gsdd.keymanager.utils.CifradoKeyManager;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

  public String generateToken(UserDetails userDetails) {
    return Jwts.builder().setSubject(userDetails.getUsername())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(Instant.now().plusSeconds(60 * 15)))
        .signWith(SignatureAlgorithm.HS256, CifradoKeyManager.KM_SALT).compact();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    return userDetails.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
  }

  public String extractUsername(String token) {
    return getClaims(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return getClaims(token).getExpiration().before(Date.from(Instant.now()));
  }

  private Claims getClaims(String token) {
    return Jwts.parser().setSigningKey(CifradoKeyManager.KM_SALT).parseClaimsJws(token).getBody();
  }
}
