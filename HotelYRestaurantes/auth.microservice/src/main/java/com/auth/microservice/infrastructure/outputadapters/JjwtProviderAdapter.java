/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.microservice.infrastructure.outputadapters;

/**
 *
 * @author estuardoramos
 */
import com.auth.microservice.application.outputports.crypto.jwt.JwtProviderOutputPort;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

@Component
public class JjwtProviderAdapter implements JwtProviderOutputPort {

  @Value("${security.jwt.secret}") private String secret;
  @Value("${security.jwt.issuer:comerdormir-auth}") private String issuer;

  private byte[] key(){ return secret.getBytes(StandardCharsets.UTF_8); }

  @Override
  public String generate(String subject, Map<String,Object> claims, long ttlSeconds) {
    var now = Instant.now();
    return Jwts.builder()
        .setIssuer(issuer)
        .setSubject(subject)
        .addClaims(claims)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
        .signWith(Keys.hmacShaKeyFor(key()), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public Map<String, Object> parse(String token) {
    var jws = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(key())).build().parseClaimsJws(token.replace("Bearer ",""));
    var map = new HashMap<String,Object>(jws.getBody());
    map.put("sub", jws.getBody().getSubject());
    return map;
  }
}