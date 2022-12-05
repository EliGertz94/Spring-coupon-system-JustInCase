package com.coupons.couponsystem.security;

public class JWTTokenProvider {
//
//    @Value("${app.jwt-secret}")
//    private String jwtSecret;
//    @Value("${app.jwt-expiration-milliseconds}")
//    private int jwtExpirationInMs;
//
//    public String generateToken(Authentication authentication){
//        String username = authentication.getName();
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMs);
//
//        String token = Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//        return token;
//    }
//
//    // get username from the token
//
//    public String getUsernameFromJWT(String token){
//        Claims claims = Jwts.parser()
//                .setSigningKey(jwtSecret)
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    // validate JWT token
//    public boolean validateToken(String token){
//        try{
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
//            return true;
//        }catch (SignatureException ex){
//            throw new CouponSystemException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
//        } catch (MalformedJwtException ex) {
//            throw new CouponSystemException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
//        } catch (ExpiredJwtException ex) {
//            throw new CouponSystemException(HttpStatus.BAD_REQUEST, "Expired JWT token");
//        } catch (UnsupportedJwtException ex) {
//            throw new CouponSystemException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
//        } catch (IllegalArgumentException ex) {
//            throw new CouponSystemException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
//        }
//    }
//
}
