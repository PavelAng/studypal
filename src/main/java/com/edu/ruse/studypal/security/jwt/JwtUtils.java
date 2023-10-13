package com.edu.ruse.studypal.security.jwt;

import com.edu.ruse.studypal.entities.Course;
import com.edu.ruse.studypal.entities.User;
import com.edu.ruse.studypal.repositories.UserRepository;
import com.edu.ruse.studypal.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
   @Autowired
   UserDetailsService userDetailsService;
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
  @Autowired
  UserRepository userRepository;

  @Value("${studypal.app.jwtSecret}")
  private String jwtSecret;

  @Value("${studypal.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(key(), SignatureAlgorithm.HS256)
        .compact();
  }
  
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key()).build()
               .parseClaimsJws(token).getBody().getSubject();
  }
  public String getUserRoleFromJwtToken(String token) {
    String username = getUserNameFromJwtToken(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    String res = String.valueOf(userDetails.getAuthorities().iterator().next());
   return res;
  }

  public Long getUserIdFromJwtToken(String token) {
    String username = getUserNameFromJwtToken(token);
    User user = userRepository.findByUsername(username).get();

    return user.getUser_id();
  }

  public List<Course> getUserCourse(String token) {
    String username = getUserNameFromJwtToken(token);
    User user = userRepository.findByUsername(username).get();

    return user.getCurrentCourses();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
}
