package org.smart.utilities.security;

import static org.smart.utilities.security.SecurityConstants.JWT_EXPIRATION;
import static org.smart.utilities.security.SecurityConstants.JWT_SECRET;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JWTGenerator {

  public String generateToken(Authentication authentication) {
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expireDate)
        .claim("authorities", authentication.getAuthorities().iterator().next().getAuthority())
        .claim("username", username)
        .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
        .compact();

  }

  public String getUsernameFromJWT(String token) {
    return Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
    }
  }

  public String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }

    return null;
  }

  public String getUsernameFromRequest(HttpServletRequest request) {
    return getUsernameFromJWT(getJWTFromRequest(request));
  }
}
