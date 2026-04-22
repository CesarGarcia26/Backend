package com.example.FormularioAutomatizacion.Web.Security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtil {


    private static final String SECRET_KEY = "tor_ux_is_my_key_1_2_3_tor_ux_is_my_key_1_2_3";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Token válido por ejemplo 8 horas
    private static final long EXPIRATION_MS = 1000L * 60 * 60 * 8;

    public static String generateToken(String username, String empresa, Map<String, Object> additionalClaims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("empresa", empresa)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(KEY, SignatureAlgorithm.HS256);

        if (additionalClaims != null && !additionalClaims.isEmpty()) {
            builder.addClaims(additionalClaims);
        }

        return builder.compact();
    }

    public static Jws<Claims> parseToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token);
    }

    public static String getUsernameFromToken(String token) throws JwtException {
        return parseToken(token).getBody().getSubject();
    }

    public static String getEmpresaFromToken(String token) throws JwtException {
        Object empresa = parseToken(token).getBody().get("empresa");
        return empresa != null ? empresa.toString() : null;
    }

    public static boolean isTokenExpired(String token) {
        try {
            Date exp = parseToken(token).getBody().getExpiration();
            return exp.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}