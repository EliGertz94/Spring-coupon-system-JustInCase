package com.coupons.couponsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTTokenProvider {

    public String generateToken(SecuredUser userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String ,Object> extraClaims, UserDetails userDetails){

        return Jwts.
                builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


    }



    public String getUserNameFromJwt(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token,SecuredUser user){
        try {
            String userName = getUserNameFromJwt(token);
          //  Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(token);
            return (userName.equals(user.getUsername())&& !isTokenExpired(token));

        }catch (Exception e){
            throw  new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    private boolean isTokenExpired(String token) {  
            return extractExpiration(token).before(new Date()); 
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey(){
      byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.JWT_SECRET);
      return Keys.hmacShaKeyFor(keyBytes);
    }

}
