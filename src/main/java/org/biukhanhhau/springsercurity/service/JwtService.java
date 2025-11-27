package org.biukhanhhau.springsercurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    static private final String SECRET = "c3F1YXJlZGl2aXNpb25hZmZlY3RuZXdzcGFwZXJ3b2xmeW91cnNlbGZkb3RidXJpZWQ=";

    public String generateToken(String username){

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)                      // Thông tin bổ sung
                .setSubject(username)                   // như tên
                .setIssuedAt(new Date(System.currentTimeMillis()))          //thời gian tạo
                .setExpiration(new Date(System.currentTimeMillis() + 1000L *60*3)) //thời gian hết hạn
                .signWith(getKey(), SignatureAlgorithm.HS256).compact();       //set cái kìa khóa
    }

    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);      // mở cái nội dung vừa được lấy ở extractAllClaims
        return claimResolver.apply(claims);                 // Lấy đúng cái món mình cần và gắn vào type claimRosolver
    }                                                     // như ở extractUserName có Claims::getSubject tức là lấy username

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())        // Lấy cái chìa khóa (ở trên)
                .build()
                .parseClaimsJws(token)          // Cấm chìa khóa vào để mở cái token này ra
                .getBody();                     // Lấy nội dung bên trong
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());   //check xem hết hạn chưa
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);      // lấy ra cái thời gian hết hạn
    }
}
