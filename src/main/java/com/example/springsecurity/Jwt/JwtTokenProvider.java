package com.example.springsecurity.Jwt;

import com.example.springsecurity.Security.CustomUserDetail;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    // chuỗi key
    @Value("${ra.jwt.secret}")
    private String JWT_SECRET;

    // thời gian hết han
    @Value("{ra.jwt.expiration}")
    private String JWT_EXPIRATION;

    // tạo jwt từ thông tin của user
    public String generateToken(CustomUserDetail customUserDetail) {
        Date now = new Date();
        Date dateExpiration = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(customUserDetail.getUsername()) // set thông tin username
                .setExpiration(dateExpiration) // set thời gian het hạn
                .setIssuedAt(now) // set thoi gian bat dau có hiệu lực
                .signWith(SignatureAlgorithm.ES512, JWT_SECRET) // giải thuật mã hóa
                .compact();
    }

    // lấy thông tin user từ jwt
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(JWT_SECRET)
                .parseClaimsJws(token).getBody();
        // trả ra userName
        return claims.getSubject();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            log.error("Expired Jwt Token");
        } catch (UnsupportedJwtException e) {
            log.error("unsupport Jwt Token");
        } catch (IllegalArgumentException e) {
            log.error("JWT String is empty");
        }
        return false;
     }
}
