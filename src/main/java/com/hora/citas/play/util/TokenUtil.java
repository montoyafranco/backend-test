package com.hora.citas.play.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;


public class TokenUtil {

    private static final String SECRET_KEY = "montoya_clave_secreta343434343434343434#";

    public static boolean validateToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return false;
            }

            String tokenWithoutBearer = token.replace("Bearer ", "");

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .build()
                    .parseClaimsJws(tokenWithoutBearer);

            // Retrieve the claims from the Jws object
            Claims claims = claimsJws.getBody();

            // Check if the claims are not empty (indicating a signed token)
            return !claims.isEmpty();
        } catch (JwtException e) {
            return false;
        }
    }

}

